package com.tjw.tchat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Mr.Tang
 * @date 2018/10/2
 * 初始化器，channel注册后会执行其中的方法
 */
public class HelloInlitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 通过socket获取管道
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        // httpservercode是netty提供的助手类，相当于拦截器
        channelPipeline.addLast("httpServerCodec",new HttpServerCodec());
        channelPipeline.addLast("customHandler",new CustomHandler());
    }
}
