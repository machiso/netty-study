package com.machi.nettystudy.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

//服务端的初始化器
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    //连接一旦被注册之后就会被创建，执行initChannel回调的方法
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //管道
        ChannelPipeline pipeline = ch.pipeline();

        //HttpServerCodec TestHttpServerHandler 多实例对象
        pipeline.addLast("httpServerCodec",new HttpServerCodec());

        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
