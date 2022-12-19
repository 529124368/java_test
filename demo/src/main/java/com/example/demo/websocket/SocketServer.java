package com.example.demo.websocket;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketServer {
    @Resource
    private SocketInitializer socketInitializer;

    @Getter
    private ServerBootstrap serverBootstrap;

    /**
     * netty服务监听端口
     */
    @Value("${netty.port:8088}")
    private int port;
    /**
     * 主线程组数量
     */
    @Value("${netty.bossThread:1}")
    private int bossThread;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    /**
     * 启动netty服务器
     * 
     * @throws InterruptedException
     */
    public void start() {
        this.init();
        try {
            ChannelFuture cf = this.serverBootstrap.bind(this.port).sync();
            System.out.println("netty 启动成功" + "Netty started on port: {" + this.port + "} (TCP) with boss thread {}"
                    + this.bossThread);
            log.info("Netty started on port: {} (TCP) with boss thread {}", this.port, this.bossThread);
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    /**
     * 初始化netty配置
     */
    private void init() {
        // 创建两个线程组，bossGroup为接收请求的线程组，一般1-2个就行
        bossGroup = new NioEventLoopGroup(this.bossThread);
        // 实际工作的线程组
        workerGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(bossGroup, workerGroup) // 两个线程组加入进来
                .channel(NioServerSocketChannel.class) // 配置为nio类型
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(this.socketInitializer); // 加入自己的初始化器
    }
}
