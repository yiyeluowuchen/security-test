package com.yiyezhiqiu.security.controller;

import com.yiyezhiqiu.security.domain.Permission;
import com.yiyezhiqiu.security.mapper.PermissionMapper;
import com.yiyezhiqiu.security.service.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    IPermissionService permissionService;

    @RequestMapping("/findAll")
    public void findAll(){
        log.info("进来findALLcoNTROLLER");
        List<Permission> list =  permissionService.findAll();
        for(Permission permission :list){
            log.info(permission.getName());
        }

    }

}
