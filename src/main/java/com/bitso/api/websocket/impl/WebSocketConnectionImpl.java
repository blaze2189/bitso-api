/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.websocket.WebSocketConnection;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jorge
 */
@Service
public class WebSocketConnectionImpl extends Observable 
implements WebSocketConnection {

    @Autowired
    protected ClientHandler bitsoWebSocketClientHandler;
    
    @Autowired
    @Qualifier("defaulHttpHeaders")
    protected HttpHeaders httpHeaders;

    @Autowired
    protected Bootstrap bootstrap;

    @Autowired
    protected EventLoopGroup eventLoopGroup;
    
    @Autowired
    protected BitsoWebSocketInitializer bitsoWebSocketInitializer;

    private Boolean isConnected;
    private Channel channel;
    
    @Value("${bitso.port}")
    private String bitsoPort;
    
    @Value("${bitso.server}")
    private String bitsoServer;

    @Override
    public void openConnection() 
        throws InterruptedException{
        Bootstrap bootsrapGroup = bootstrap.group(eventLoopGroup);
        Bootstrap bootsrapChannel = bootsrapGroup.channel(NioSocketChannel.class);
        bootsrapChannel.handler(bitsoWebSocketInitializer);
        channel=bootstrap.connect(bitsoServer, Integer.parseInt(bitsoPort)).sync().channel();//Server name
        ChannelFuture cF = bitsoWebSocketClientHandler.getChannelPromise();
        cF.sync();
        setConnected(Boolean.TRUE);
    }
    
    public void setConnected(Boolean connected) {
        isConnected = connected;
        setChanged();
        notifyObservers(isConnected);
    }
    
    public void setMessageReceived(String messageReceived){
        setChanged();
        notifyObservers(messageReceived);
    }

    @Override
    public void closeConnection() throws InterruptedException{
        channel.writeAndFlush(new CloseWebSocketFrame());
        channel.closeFuture().sync();
        eventLoopGroup.shutdownGracefully();
    }

    @Override
    public void close() throws Exception {
        this.closeConnection();
    }

    @Override
    public boolean isConeccted() {
        return isConnected;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }
}