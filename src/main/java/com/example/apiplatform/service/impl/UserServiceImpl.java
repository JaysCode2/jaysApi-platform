package com.example.apiplatform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jays.jaysapicommon.common.ErrorCode;
import com.example.apiplatform.exception.BusinessException;
import com.example.apiplatform.service.UserService;
import com.example.apiplatform.mapper.UserMapper;
import com.example.apiplatform.vo.UserVo;
import com.jays.jaysapicommon.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
* @author chenjiexiang
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-02-07 21:09:22
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    private static final  String salt = "jays";

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public UserVo registerUser(User user) {
        String account = user.getUserAccount();
        String password = user.getUserPassword();
        //判空
        if(StringUtils.isAnyBlank(account,password)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        //校验账号只能在4到8之间，密码只能在6到10之间
        if(account.length()<4 || account.length()>8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号只能在4位到8位之间");
        }
        if(password.length()<6 || password.length()>10){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码只能在6位到10位之间");
        }
        //得判断账号是否重复
        User judgeUser = this.getUserByAccount(account);
        if(judgeUser!=null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号已存在");
        }
        //md5且加盐给密码加密
        user.setUserPassword(DigestUtils.md5DigestAsHex((salt+password).getBytes()));
        //分配 accessKey,secretKey;
        String accessKey = DigestUtil.md5Hex(salt+account+ RandomUtil.randomNumbers(5));
        String secretKey = DigestUtil.md5Hex(salt+account+ RandomUtil.randomNumbers(8));
        user.setAccessKey(accessKey);
        user.setSecretKey(secretKey);
        //保存进数据库
        boolean registerUser = this.save(user);
        if(!registerUser){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"注册失败");
        }
        //返回脱敏数据
        UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);
        return userVo;
    }

    @Override
    public UserVo userLogin(User user, HttpServletRequest request) {
        String account = user.getUserAccount();
        String password = user.getUserPassword();
        //判断账号密码是否为空
        if(StringUtils.isAnyBlank(account,password)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        //校验账号只能在4到8之间，密码只能在6到10之间
        if(account.length()<4 || account.length()>8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号只能在4位到8位之间");
        }
        if(password.length()<6 || password.length()>10){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码只能在6位到10位之间");
        }
        //对比数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String md5Password = DigestUtils.md5DigestAsHex((salt+password).getBytes());
        queryWrapper.eq(account!=null,User::getUserAccount,account);
        queryWrapper.eq(password!=null,User::getUserPassword,md5Password);
        User loginUser = this.getOne(queryWrapper);
        if(loginUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码错误");
        }
        //用户存在，存入session会话
        request.getSession().setAttribute("loginUser",loginUser);
        UserVo userVo = BeanUtil.copyProperties(loginUser, UserVo.class);
        return userVo;
    }

    /**
     * 根据账号获取用户信息
     */
    public User getUserByAccount(String account){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount,account);
        return this.getOne(queryWrapper);
    }
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute("loginUser");
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）,这是为了保证数据一致性
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }
}




