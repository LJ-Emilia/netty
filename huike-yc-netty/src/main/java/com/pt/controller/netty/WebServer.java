package com.pt.controller.netty;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;


/**
 * @author: Lijie
 * @Date: 2019/3/7 11:14
 */
public class WebServer {

    private static Logger logger = LoggerFactory.getLogger(WebServer.class);

    public static void main(String[] args) {
//        ApplicationContext ctx = new AnnotationConfigApplicationContext();
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-dao.xml",
                "classpath:spring/applicationContext-service.xml",
                "classpath:spring/applicationContext-trans.xml");
        Integer port = 6001;

        logger.debug("port:{}",port);

        DispatcherServlet servlet = getDispatcherServlet(ctx);
        NettyHttpServer server = new NettyHttpServer(port,servlet);
        server.start();
        logger.debug("server:{}","start");

    }

    public static DispatcherServlet getDispatcherServlet(ApplicationContext ctx){

        XmlWebApplicationContext mvcContext = new XmlWebApplicationContext();
        mvcContext.setConfigLocation("classpath:spring/springmvc.xml");
        mvcContext.setParent(ctx);
//        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
//        mvcContext.register(WebInitializer.class,BaseMvcConfig.class);
        mvcContext.setParent(ctx);
        MockServletConfig servletConfig = new MockServletConfig(mvcContext.getServletContext(), "dispatcherServlet");
        DispatcherServlet dispatcherServlet = new DispatcherServlet(mvcContext);
        try {
            dispatcherServlet.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return dispatcherServlet;
    }
}




