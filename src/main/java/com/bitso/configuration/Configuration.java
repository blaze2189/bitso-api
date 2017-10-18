/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.configuration;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

/**
 *
 * @author Jorge
 */
@Component
public class Configuration {
 
	RestTemplate restTemplate = new RestTemplate();
	
	
    
    @Bean
    public RestTemplate restTemplate(){
    return restTemplate;
    }

    @Bean(name = "websocketClientHandshaker")
    public WebSocketClientHandshaker websocketClientHandshaker() throws URISyntaxException {
        final URI URI = new URI("wss://ws.bitso.com");
        return WebSocketClientHandshakerFactory.newHandshaker(
                URI, WebSocketVersion.V08, null, false,
                new DefaultHttpHeaders());
    }

    @Bean(name = "defaulHttpHeaders")
    public HttpHeaders defaultHttpHeaders() {
        return new DefaultHttpHeaders();
    }

    @Bean
    public Bootstrap bootstrap() {
        return new Bootstrap();
    }

    @Bean
    public EventLoopGroup eventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public HttpClientCodec httpClientCodec() {
        return new HttpClientCodec();
    }

    @Bean
    public HttpObjectAggregator httpClientObjectAggregator() {
        return new HttpObjectAggregator(8192);
    }

}
