package com.machi.nettystudy.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

//自定义处理器
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端的请求并向客户端返回响应的方法
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        ByteBuf content = Unpooled.copiedBuffer("Hello World",CharsetUtil.UTF_8);

        //http响应
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

        //返回客户端
        ctx.writeAndFlush(response);
    }
}
