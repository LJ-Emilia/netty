package com.pt.controller.listener;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.pt.controller.netty.Server.MyServer;

@Service
public class WebContextListener implements InitializingBean, ServletContextAware{

	
	@Autowired
	private MyServer myServer;

	@Override
	public void setServletContext(ServletContext servletContext) {
		try {
			myServer.bind(12345);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	 
}
