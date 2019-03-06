package com.pt.controller.netty.Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.alibaba.fastjson.JSON;
//import com.pt.pojo.threed.AppVersion;
//import com.pt.service.threed.AppVersionService;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.List;

/**
 * Created by ywy on 16/9/13.
 */
@Sharable
@Component
public class HelloServerHandler extends ChannelHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloServerHandler.class);

    private String result="";
//	@Autowired
//    private AppVersionService appVersionService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {
            if(! (msg instanceof FullHttpRequest)){
                result="未知请求!";
                send(ctx, result, HttpResponseStatus.BAD_REQUEST);
                return;
            }
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            String path=httpRequest.uri(); //获取路径
            String body = getBody(httpRequest); //获取参数
            HttpMethod method=httpRequest.method();//获取请求方法

            LOGGER.info("***********************************************************");
            LOGGER.info("调用时间:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            LOGGER.info("请求方法:{}", method);
            LOGGER.info("获取路径:{}", path);
            LOGGER.info("传入参数:{}", body);
            send(ctx,"ok",HttpResponseStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
       
    /**
     * 格式转换
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
	private ByteBuf getSendByteBuf(String message) throws UnsupportedEncodingException {
        byte[] req = message.getBytes("UTF-8");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);

        return pingMessage;
    }
    
    

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

//    public AppVersionService getAppVersionService() {
//        return appVersionService;
//    }
//
//    public void setAppVersionService(AppVersionService appVersionService) {
//        this.appVersionService = appVersionService;
//    }

    /**
     * 获取body参数
     * @param request
     * @return
     */
     private String getBody(FullHttpRequest request){
         ByteBuf buf = request.content();
         return buf.toString(CharsetUtil.UTF_8);
     }
     /**
     * 发送的返回值
     * @param ctx     返回
     * @param context 消息
     * @param status 状态
     */
     private void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status) {
         FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
         response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
         ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
     }
     /**
     * 建立连接时，返回消息
     */
     @Override
     public void channelActive(ChannelHandlerContext ctx) throws Exception {
         System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
         ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ ");
         super.channelActive(ctx);
     }

}
