/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server.parse;

import com.twocater.diamond.netty.AbstractNettyConnectorFactory;
import com.twocater.diamond.server.HttpContext;
import com.twocater.diamond.server.NettyServer;
import com.twocater.diamond.server.Server;
import com.twocater.diamond.server.ServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
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

            AbstractNettyConnectorFactory connectorFactory = (AbstractNettyConnectorFactory) Class.forName(protocol.getConnectorFactory()).newInstance();
            connectorFactory.setConnectorConfig(connectorConfig);
            server.addConnector(connectorFactory.createConnector(server));
        }
        return server;

    }

}
