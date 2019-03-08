//package com.pt.controller.netty.Client;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerAdapter;
//import io.netty.channel.ChannelHandlerContext;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * Created by ywy on 16/9/13.
// */
//public class HelloClientHandler extends ChannelHandlerAdapter{
//
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.fireChannelActive();
//
//        String body = "Test==>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
//                + System.getProperty("line.separator");
//        ByteBuf msg = Unpooled.buffer(body.getBytes().length);
//        msg.writeBytes(body.getBytes());
//        ctx.writeAndFlush(msg);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        String body = (String) msg;
//        System.out.println("client receive :" + body);
//    }
//}
