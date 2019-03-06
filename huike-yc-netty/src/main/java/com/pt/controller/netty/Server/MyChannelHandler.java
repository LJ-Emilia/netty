package com.pt.controller.netty.Server;

import com.pt.controller.netty.Server.HelloServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ywy on 16/9/13.
 */
@Service
public class MyChannelHandler extends ChannelInitializer<SocketChannel>{


    @Autowired
    private HelloServerHandler helloServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//        socketChannel.pipeline().addLast(new StringDecoder());
        // 请求消息解码器
        socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
        // 目的是将多个消息转换为单一的request或者response对象
        socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
        //响应解码器
        socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
        socketChannel.pipeline().addLast(helloServerHandler);
    }



}
