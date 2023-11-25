package com.picsart.intership.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Beans {
    @Bean
    public ModelMapper getModelMapperBean(){
        return new ModelMapper();
    }
}
