//package com.zhaofujun.nest.demo.infrastructure.repositories;
//
//import com.zhaofujun.automapper.builder.ClassMappingBuilder;
//import com.zhaofujun.automapper.mapping.ClassMapping;
//import com.zhaofujun.nest.demo.domain.User;
//import com.zhaofujun.nest.demo.infrastructure.repositories.dao.po.UserPO;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AutoMapperConfiguration {
//    @Bean
//    public ClassMapping userPO2User() {
//        return new ClassMappingBuilder(UserPO.class, User.class, true)
//                .builder();
//    }
//}
