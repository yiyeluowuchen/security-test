package com.yiyezhiqiu.security.mapper;

import com.yiyezhiqiu.security.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    /**
     * 以后看见b绑定异常，
     * 检查你的mapping，在yml中是否配置 classpath:mapping/*.xml，结构即使对了也要看下层级关系对了没
     * namespace是具体到某个接口
     * @param id
     * @return
     */

    List<Permission> findByAdminUserId(int id);
    List<Permission> findAll();


}
