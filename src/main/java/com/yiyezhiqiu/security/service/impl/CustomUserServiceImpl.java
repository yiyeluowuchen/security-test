package com.yiyezhiqiu.security.service.impl;

import com.yiyezhiqiu.security.domain.Permission;
import com.yiyezhiqiu.security.domain.UserDao;
import com.yiyezhiqiu.security.mapper.PermissionMapper;
import com.yiyezhiqiu.security.mapper.UserMapper;
import com.yiyezhiqiu.security.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomUserServiceImpl  implements IUserService,UserDetailsService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PermissionMapper permissionMapper;

    /**
     *  当有用户登陆时，该方法会获得用户名，不会获得密码，然后通过名字去验证。
     * 这个方法不对名字和密码去验证。交给了spring security后，再去验证账号，密码，这里我觉得验证就是在webSecurityConfig中
     * 来看是否登陆成功，成功后就会用户信息，权限放在内存中，供后面使用
     * @param username
     * @return
     */
    @Override
        //返回 user和user拥有的权限功能 by username
        public UserDetails loadUserByUsername(String username) {
            UserDao user = userMapper.findByUserName(username);
            //log.info(user.getUsername());
            if (user != null) {
                log.info(user +"我就不信真进来了这里当user为空的时候");
                BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
                String newPassword = pass.encode(user.getPassword());


                List<Permission> permissions = permissionMapper.findByAdminUserId(user.getId());
                log.info(permissions.get(0)+"");
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (Permission permission : permissions) {
                    if (permission != null && permission.getName()!=null) {

                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                        //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行权限验证时会使用GrantedAuthority 对象。
                        grantedAuthorities.add(grantedAuthority);
                    }
                }
                return new User(user.getUsername(),newPassword, grantedAuthorities);
            } else {
                log.info("当没有该用户的时候就进来了这里，然后就会失败，websecurityConfig中有配置失败怎么样，然后在login.html就会失败了的具体执行措施");
                throw new UsernameNotFoundException("admin: " + username + " do not exist!");
            }
        }


}
