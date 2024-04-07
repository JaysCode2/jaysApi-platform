package com.example.apiplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.apiplatform.annotation.AuthCheck;
import com.example.apiplatform.constant.CommonConstant;
import com.example.apiplatform.domain.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.example.apiplatform.domain.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.example.apiplatform.domain.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.example.apiplatform.domain.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.example.apiplatform.domain.enums.InterfaceInfoStatusEnum;
import com.example.apiplatform.exception.BusinessException;
import com.example.apiplatform.service.InterfaceInfoService;
import com.example.apiplatform.service.UserService;
import com.google.gson.Gson;
import com.jays.client.JaysApiClient;
import com.jays.jaysapicommon.common.*;
import com.jays.jaysapicommon.domain.InterfaceInfo;
import com.jays.jaysapicommon.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private JaysApiClient jaysApiClient;

    //增删改查

    /**
     * 增加接口信息
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest,
                                               HttpServletRequest request){
        //判空
        if (interfaceInfoAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //实体信息
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest,interfaceInfo);
        Long userId = userService.getLoginUser(request).getId();
        interfaceInfo.setUserId(userId);
        //校验，后期再开发
        //添加
        boolean add = interfaceInfoService.save(interfaceInfo);
        if (!add){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        Long newInterFaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterFaceInfoId);
    }

    /**
     * 删除接口信息
     * 仅管理员和该接口创建人可以删除
     * @param deleteRequest
     * @param request
     * @return
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteInterface(@RequestBody DeleteRequest deleteRequest,
                                                 HttpServletRequest request){
        if(deleteRequest == null || deleteRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User nowUser = userService.getLoginUser(request);
        Long nowUserId = nowUser.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(deleteRequest.getId());
        if(interfaceInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"要删除的接口信息不存在");
        }
        Long oldUserId = interfaceInfo.getUserId();
        //判断是否有权限,仅本人和管理员有权限删除
        if(!nowUserId.equals(oldUserId) && !(nowUser.getUserRole()).equals("admin")){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //执行删除
        boolean delete = interfaceInfoService.removeById(deleteRequest.getId());
        return ResultUtils.success(delete);
    }

    /**
     * 修改接口信息
     * 本人和管理员可以改
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<InterfaceInfo> updateInterface(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                       HttpServletRequest request){
        //判空
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest,interfaceInfo);
        //参数校验,后续再开发吧
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(interfaceInfoUpdateRequest.getId());
        if(oldInterfaceInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"要修改的接口信息不存在");
        }
        //判断是否有权限,仅本人和管理员有权限删除
        User nowUser = userService.getLoginUser(request);
        Long nowUserId = nowUser.getId();
        Long oldUserId = oldInterfaceInfo.getUserId();
        if(!nowUserId.equals(oldUserId) && !(nowUser.getUserRole()).equals("admin")){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Boolean update = interfaceInfoService.updateById(interfaceInfo);
        if(!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    //发布下线，调试
    /**
     * 发布
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 判断该接口是否可以调用
//        com.jays.jaysapiclientsdk.model.User user = new com.jays.jaysapiclientsdk.model.User();
        com.jays.model.User user = new com.jays.model.User();
        user.setUserName("test");
        String username = jaysApiClient.getUserNameByPost(user);
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
        }
        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                      HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 测试调用
     *
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                    HttpServletRequest request) {
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = interfaceInfoInvokeRequest.getId();
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (oldInterfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        //这个为真是业务
        JaysApiClient tempClient = new JaysApiClient(accessKey, secretKey);
        //这个为为跑通流程测试用的
//        JaysApiClient tempClient = new JaysApiClient("jays", "abcd");
        //gson可以，hutool也可以
        Gson gson = new Gson();
        com.jays.model.User user = gson.fromJson(userRequestParams, com.jays.model.User.class);
        //{\"userName\":\"jays\"}，因为json转译过程中 \" ==> ",所以在knife4j里传参数的话你要用"\替换
//        com.jays.model.User user = JSONUtil.toBean(userRequestParams, com.jays.model.User.class);
        log.info("333");
        log.info("user类名字：{}",user.getUserName());
        String usernameByPost = tempClient.getUserNameByPost(user);
        return ResultUtils.success(usernameByPost);
    }
}
