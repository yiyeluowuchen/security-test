package com.yiyezhiqiu.security.mapper;

import com.yiyezhiqiu.security.domain.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    UserDao  findByUserName(String username);


}
