package com.machi.nettystudy.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) throws InterruptedException {

        //定义两个基于NIO的事件循环组,死循环,不断的接收客户端的连接
        //bossGroup：不断的从客户端接收连接，但是不对连接做任何处理，转给workGroup
        //workGroup：对连接进行真正的处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //服务端启动类，对启动做了一定的封装
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //NioServerSocketChannel 反射方式创建
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
