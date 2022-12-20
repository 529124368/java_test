package com.example.demo.websocket;

import java.text.SimpleDateFormat;

import org.apache.ibatis.javassist.Loader.Simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketHandler extends ChannelInboundHandlerAdapter {
    public static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 读取到客户端发来的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 由于我们配置的是 字节数组 编解码器，所以这里取到的用户发来的数据是 byte数组
        ByteBuf data = (ByteBuf) msg;
        log.info("收到消息: " + data, CharsetUtil.UTF_8);
        System.out.println("收到消息: " + data.toString(CharsetUtil.UTF_8));
        // 给其他人转发消息
        for (Channel client : clients) {
            if (!client.equals(ctx.channel())) {
                client.writeAndFlush(data);
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        SimpleDateFormat nowTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        log.info("新的客户端链接：" + ctx.channel().id().asShortText(), CharsetUtil.UTF_8);
        clients.writeAndFlush(
                Unpooled.copiedBuffer(nowTime + "用户:" + ctx.channel().remoteAddress() + "上线了", CharsetUtil.UTF_8));
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        clients.writeAndFlush(Unpooled.copiedBuffer("用户:" + ctx.channel().remoteAddress() + "下线了", CharsetUtil.UTF_8));
        clients.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // cause.printStackTrace();
        System.out.println("客户端" + ctx.channel().id() + "有链接丢失");
        ctx.channel().close();
        clients.remove(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("我收到你的消息了", CharsetUtil.UTF_8));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent status = (IdleStateEvent) evt;
//            switch (status.state()) {
//                case READER_IDLE:
//                    System.out.println("has read idle");
//                case WRITER_IDLE:
//                    System.out.println("has write idle");
//                case ALL_IDLE:
//                    System.out.println("has all idle");
//                default:
//                    break;
//            }
//        }
    }
}
