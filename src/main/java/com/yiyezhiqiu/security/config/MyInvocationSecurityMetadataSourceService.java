package com.yiyezhiqiu.security.config;


import com.yiyezhiqiu.security.domain.Permission;
import com.yiyezhiqiu.security.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 实现了FilterInvoacationSecurityMetadataSource 接口，主要是
 * 加载缓存权限名称和路径，通过请求路径去获得请求名称，供给后面决策器的使用
 *
 */

@Configuration
@Slf4j
public class MyInvocationSecurityMetadataSourceService  implements
        FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionMapper permissionMapper;

    //缓存
    private HashMap<String, Collection<ConfigAttribute>> map =null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;

        log.info("进来loadResource");

        List<Permission> permissions = permissionMapper.findAll();
        log.info(permissions.get(0)+"");
        for(Permission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl(), array);
        }

    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行，放行也就是说可以访问
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        log.info("进来getAttributes");

        if(map ==null) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();

            matcher = new AntPathRequestMatcher(resUrl);
            log.info(resUrl+"**"+request+"**"+matcher+"**"+request);
            if(matcher.matches(request)) {
                log.info("进来"+map.get(resUrl));
                return map.get(resUrl);
            }
        }

        log.info("没有url匹配就出来了");
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}