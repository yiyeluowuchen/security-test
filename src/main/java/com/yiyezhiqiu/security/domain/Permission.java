package com.yiyezhiqiu.security.domain;

import lombok.Data;

@Data
public class Permission {
    private int id;
    private String name;
    private String descript;
    private String url;
    private int pid;


}
