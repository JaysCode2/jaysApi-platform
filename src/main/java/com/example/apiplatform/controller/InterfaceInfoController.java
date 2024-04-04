package com.example.apiplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.apiplatform.annotation.AuthCheck;
import com.example.apiplatform.common.BaseResponse;
import com.example.apiplatform.common.DeleteRequest;
import com.example.apiplatform.common.ErrorCode;
import com.example.apiplatform.common.ResultUtils;
import com.example.apiplatform.constant.CommonConstant;
import com.example.apiplatform.domain.InterfaceInfo;
import com.example.apiplatform.domain.User;
import com.example.apiplatform.domain.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.example.apiplatform.domain.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.example.apiplatform.domain.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.example.apiplatform.exception.BusinessException;
import com.example.apiplatform.service.InterfaceInfoService;
import com.example.apiplatform.service.UserService;
import com.jays.client.JaysApiClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
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
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"要删除的书籍信息不存在");
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
}
