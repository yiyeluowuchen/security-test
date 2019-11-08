package com.yiyezhiqiu.security.service;

import com.yiyezhiqiu.security.domain.Permission;

import java.util.List;

public interface IPermissionService {
    List<Permission>  findAll();
}
