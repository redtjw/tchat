package com.tjw.tchat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Tang
 * @date 2018/10/8
 */
@Component
@PropertySource("classpath:/application.properties")
public class WSServer {
    private Integer port=8088;
    private static class SingletonWSServer{
        static final WSServer instance = new WSServer();
    }
    public static WSServer getInstance(){
        return SingletonWSServer.instance;
    }

    private WSServer() {
         mainGroup = new NioEventLoopGroup();
         subGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(mainGroup,subGroup).channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitializer());
    }

    public void start(){
        this.channelFuture = serverBootstrap.bind(port);
        System.err.println("netty 服务器启动完毕[prot-]:"+port);
    }
    private  EventLoopGroup mainGroup;
    private  EventLoopGroup subGroup;
    private ServerBootstrap  serverBootstrap;
    private ChannelFuture channelFuture;
}
