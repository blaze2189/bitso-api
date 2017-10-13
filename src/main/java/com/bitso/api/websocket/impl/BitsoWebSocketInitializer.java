/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoWebSocketInitializer extends ChannelInitializer<SocketChannel> {

    @Value("${bitso.url}")
    private String bitsoUrl;

    @Value("${bitso.port}")
    private String bitsoPort;

    @Autowired
    private HttpObjectAggregator httpObjectAggregator;

    @Autowired
    private HttpClientCodec httpClientCodec;

    @Autowired
    ClientHandler bitsoWebSocketClientHandler;

    @Override
    public void initChannel(SocketChannel socketChannel) throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient().
                trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        ChannelPipeline channelPipeline
                = socketChannel.pipeline();
        channelPipeline.addLast(sslContext.newHandler(
                socketChannel.alloc(),
                bitsoUrl,
                Integer.parseInt(bitsoPort)));
        channelPipeline.addLast(httpClientCodec,
                httpObjectAggregator,
                bitsoWebSocketClientHandler);
    }

}
