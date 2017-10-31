/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.websocket.WebSocketConnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge
 */
@Component
public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Autowired
	private WebSocketConnection webSocektConnection;

	@Autowired
	@Qualifier("websocketClientHandshaker")
	private WebSocketClientHandshaker webSocketClientHandshaker;
	private ChannelPromise channelPromise;

	public ChannelFuture getChannelPromise() {
		return channelPromise;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		channelPromise = ctx.newPromise();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		webSocketClientHandshaker.handshake(ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();

		boolean handShakeComplete = !webSocketClientHandshaker.isHandshakeComplete();

		if (handShakeComplete) {
			webSocketClientHandshaker.finishHandshake(channel, (FullHttpResponse) msg);
			channelPromise.setSuccess();
			return;
		}

		if (msg instanceof FullHttpResponse) {
			FullHttpResponse response = (FullHttpResponse) msg;
			throw new Exception("Unexpected FullHttpResponse: " + response.content().toString(CharsetUtil.UTF_8) + ") "
					+ response.status());
		}

		WebSocketFrame frame = (WebSocketFrame) msg;
		if (frame instanceof TextWebSocketFrame) {
			TextWebSocketFrame testWebSocketFrame = (TextWebSocketFrame) frame;
			if (testWebSocketFrame != null && testWebSocketFrame.text() != null) {
				setMessageReceived(testWebSocketFrame.text());
			}
		}

		if (frame instanceof CloseWebSocketFrame) {
			setConnected(Boolean.FALSE);
		}
	}

	private void setMessageReceived(String message) {
		if (message != null) {
			((WebSocketConnectionImpl) webSocektConnection).setMessageReceived(message);
		}
	}

	private void setConnected(Boolean isConnected) {
		((WebSocketConnectionImpl) webSocektConnection).setConnected(isConnected);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (!channelPromise.isDone()) {
			channelPromise.setFailure(cause);
		}
		ctx.close();
	}

}
