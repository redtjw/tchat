package com.tjw.tchat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Mr.Tang
 * @date 2018/9/26
 *  客户端发送一个请求，服务端返回hello netty
 */
public class HelloServer {
    public static void main(String[] args) throws Exception {
        // 设置主线程
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        // 设置从线程租
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            // 创建启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HelloInlitializer());
            // 启动
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            // 停止时监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            mainGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
