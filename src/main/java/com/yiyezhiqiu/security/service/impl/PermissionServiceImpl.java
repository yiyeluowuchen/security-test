package com.yiyezhiqiu.security.service.impl;

import com.yiyezhiqiu.security.domain.Permission;
import com.yiyezhiqiu.security.mapper.PermissionMapper;
import com.yiyezhiqiu.security.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAll() {
        return  permissionMapper.findAll();
    }
}
