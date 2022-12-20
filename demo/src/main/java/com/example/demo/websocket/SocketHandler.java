package com.example.demo.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelId;
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
    private static  final Map<ChannelId, Integer> userMap = new HashMap<>();
    private static int no=0;

    private  SimpleDateFormat nowTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    /**
     * 读取到客户端发来的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 由于我们配置的是 字节数组 编解码器，所以这里取到的用户发来的数据是 byte数组
        ByteBuf data = (ByteBuf) msg;
        String msgtps = data.toString(CharsetUtil.UTF_8);

        System.out.println("收到来自客户端=》"+userMap.get(ctx.channel().id())+"的消息: " + msgtps);
        // 给其他人转发消息
        clients.forEach(u->{
            if(!u.equals(ctx.channel())) {
                u.writeAndFlush(Unpooled.copiedBuffer("客户端:"+userMap.get(ctx.channel().id())+"=>"+msgtps,CharsetUtil.UTF_8));
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Date date = new Date();
        log.info("新的客户端链接：" + ctx.channel().id().asShortText(), CharsetUtil.UTF_8);
        userMap.put(ctx.channel().id(),no);
        no++;
        clients.writeAndFlush(
                Unpooled.copiedBuffer(nowTime.format(date) + "用户:" + userMap.get(ctx.channel().id()) + "上线了", CharsetUtil.UTF_8));
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Date date = new Date();
        clients.writeAndFlush(Unpooled.copiedBuffer(nowTime.format(date)+"用户:" + userMap.get(ctx.channel().id()) + "下线了", CharsetUtil.UTF_8));
        clients.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // cause.printStackTrace();
        System.out.println("客户端" + userMap.get(ctx.channel().id()) + "掉线了");
        ctx.channel().close();
        clients.remove(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        //ctx.writeAndFlush(Unpooled.copiedBuffer("我收到你的消息了", CharsetUtil.UTF_8));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent status = (IdleStateEvent) evt;
//            switch (status.state()) {
////                case READER_IDLE:
////                    System.out.println("has read idle");
////                case WRITER_IDLE:
////                    System.out.println("has write idle");
//                case ALL_IDLE:
//                    //将客户端踢下线
//                    ctx.channel().close();
//                    clients.remove(ctx.channel());
//                default:
//                    break;
//            }
//        }
    }
}
