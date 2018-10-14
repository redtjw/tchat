package com.tjw.tchat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @author Mr.Tang
 * @date 2018/10/9
 * 处理消息的handler
 * textwebSocketFrame:在netty中，是用于websocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
            throws Exception {
        // 获取客户端传递过来的内同
        String content = msg.text();
        System.out.println("接收到的数据:"+content);
//        for (Channel channel:clients){
//            // 需要用textwebsocketframe作为载体
//            channel.writeAndFlush(new TextWebSocketFrame("[服务器在]"+LocalDateTime.now()
//                    +"，接收到消息，消息为:"+content));
//        }
        // 也可以直接使用cliens的writeandflush方法
        clients.writeAndFlush(new TextWebSocketFrame("[服务器在]"+LocalDateTime.now()
                +"，接收到消息，消息为:"+content));
    }

    @Override
    /**
     * 打开客户端时，将channel放到group里
     */
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端上线："+ctx.channel().id().asLongText());
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当出发handlerRemoved方法，clients会自动移除
//        clients.remove(ctx.channel());
        System.out.println("客户端断开，对应的长ID："+ctx.channel().id().asLongText());
        System.out.println("客户端断开，对应的短ID："+ctx.channel().id().asShortText());
    }
}
