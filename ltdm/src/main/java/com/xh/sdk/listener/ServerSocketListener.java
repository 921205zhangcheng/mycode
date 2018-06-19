package com.xh.sdk.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.core.sgip.server.SGIPServer;

public class ServerSocketListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
		
		
//		SGIPServer socketServer = new SGIPServer();
//		
//		socketServer.getInstance().startSGIPService();
	}


}
