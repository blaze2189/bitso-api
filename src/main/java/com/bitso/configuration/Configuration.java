/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.configuration;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge
 */
@Component
public class Configuration {

    private Set setBistoWebSocket = new HashSet();
    
//    @Bean(name = "bitsoWebSocektClientHandler")
//    public ClientHandler bitsoWebSocketClientHandler()throws URISyntaxException{
//        final String URL="wss://ws.bitso.com";
//        final URI URI = new URI(URL);
//        return new WebSocketClientHandler(
//                        WebSocketClientHandshakerFactory.newHandshaker(
//                                mUri, WebSocketVersion.V08, null, false,
//                                new DefaultHttpHeaders()));
//    }
    @Bean(name = "websocketClientHandshaker")
    public WebSocketClientHandshaker websocketClientHandshaker() throws URISyntaxException {
        final URI URI = new URI("wss://ws.bitso.com");
        return WebSocketClientHandshakerFactory.newHandshaker(
                URI, WebSocketVersion.V08, null, false,
                new DefaultHttpHeaders());
    }
//
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

//    public SslContext getSslContext(){
//        return SslContextBuilder.forClient(). trustManager(InsecureTrustManagerFactory.INSTANCE).build();
//    }
    @Bean
    public HttpClientCodec httpClientCodec() {
        return new HttpClientCodec();
    }

    @Bean
    public HttpObjectAggregator httpClientObjectAggregator() {
        return new HttpObjectAggregator(8192);
    }
    
    @Bean(name="setBitsoWebSocket")
    public Set setBitsoWebSocket(){
        return setBistoWebSocket;
    }

}
