package com.tjw.tchat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Mr.Tang
 * @date 2018/10/8
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        // websocket基于http，所以需要http的解编码器
        channelPipeline.addLast(new HttpServerCodec());
        // 对写达数据流的支持
        channelPipeline.addLast(new ChunkedWriteHandler());
        // 对httpmessage进行聚合，聚合fullhttprequest和fullhttpresponse
        // 几乎在所有的netty编程中都会使用此handler
        channelPipeline.addLast(new HttpObjectAggregator(1024*64));
        // =======================以上是支持http协议

        /**
         * netty 服务器处理的协议，用于指定给客户端访问的路由地址:ws
         * 本handler会帮助你处理一些繁杂的事情
         * 会处理握手动作:handshaking(close,ping,pong) ping+pong=心跳
         * 对于websocket，都是以frames进行传输的，不同数据类型对应的frames也不同
         */
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义handler
        channelPipeline.addLast(new ChatHandler());
    }
}
