package com.demo.licensingservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;
}
