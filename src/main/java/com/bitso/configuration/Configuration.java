/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jorge
 */
@Component
public class Configuration {
    
    RestTemplate restTemplate = new RestTemplate();
    
    @Bean
    public RestTemplate restTemplate(){
//            RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
//    List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
//    for (HttpMessageConverter<?> converter : converters) {
//        if (converter instanceof MappingJackson2HttpMessageConverter) {
//            MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
//            jsonConverter.setObjectMapper(new ObjectMapper());
//            jsonConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET), new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
//        }
//    }
    return restTemplate;
    }
    
}
