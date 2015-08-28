/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.server.parse;

import com.twocater.diamond.core.netty.AbstractNettyConnectorFactory;
import com.twocater.diamond.core.protocol.http.HttpContext;
import com.twocater.diamond.core.server.NettyServer;
import com.twocater.diamond.core.server.Server;
import com.twocater.diamond.core.server.ServerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *根据spring xml文件创建server
 * @author cpaladin
 */
public class SpringXmlServerFactory implements ServerFactory {

    private static final String SPRING_APPLICATION_CONTEXT_XML = "diamondSpringApplicationContext.xml";

    @Override
    public Server createServer() throws Exception {
        NettyServer server = new NettyServer();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(SPRING_APPLICATION_CONTEXT_XML);
        ServerConfig serverConfig = applicationContext.getBean("serverConfig", ServerConfig.class);

        for (ConnectorConfig connectorConfig : serverConfig.getConnectorConfigs()) {
            ProtocolSupport protocol = ProtocolSupport.valueOf(connectorConfig.getProtocol());

            // 根据协议创建netty连接工厂
            AbstractNettyConnectorFactory connectorFactory = (AbstractNettyConnectorFactory) Class.forName(protocol.getConnectorFactory()).newInstance();
            // 设置连接器配置到连接工厂
            connectorFactory.setConnectorConfig(connectorConfig);
            // 添加连接器到netty server
            server.addConnector(connectorFactory.createConnector(server));
        }
        server.setServerConfig(serverConfig);
        return server;

    }

}
