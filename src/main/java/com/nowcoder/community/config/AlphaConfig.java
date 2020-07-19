package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration// 这个是普通的类的配置注解
public class AlphaConfig {

    @Bean//方法名就是bean的名字
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
    }
}
