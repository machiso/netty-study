package com.machi.nettystudy.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //channel组，用来保存channel对象
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            //不是自身
            if (channel != ch){
                ch.writeAndFlush(channel.remoteAddress()+" 发送的消息:" + msg + "\n");
            }else {
                ch.writeAndFlush("【自己】"+msg+"\n");
            }
        });
    }

    //客户端和服务器建立好连接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //连接对象
        Channel channel = ctx.channel();
        //广播给其他channel，告诉有新的连接进来
        channelGroup.writeAndFlush("【服务器】- "+channel.remoteAddress()+" 加入\n");
        //告诉其他连接xxx已经加入，加入到channel组
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】- "+channel.remoteAddress()+" 离开\n");
//        channelGroup.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
