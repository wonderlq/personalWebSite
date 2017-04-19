package com.website.learn;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用程序启动器
 *
 * @author qi.li
 * @since 1.0.0
 * Created on 2016-07-24 16:23
 */
@SpringBootApplication
@ImportResource("classpath*:spring*.xml")
@MapperScan("com.website.learn.dao.dal.mapper")
@EnableTransactionManagement
public class ApplicationStarter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.http.port:8890}")
    private int httpPort;

    @Bean
    public Integer port() {
        logger.info("###############################");
        logger.info("### http server port = " + httpPort + " ###");
        logger.info("###############################");
        return httpPort;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port());
        return connector;
    }

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStarter.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("merlin-website starting......");
        SpringApplication.run(ApplicationStarter.class, args);
        LOGGER.info("merlin-website started!");
    }
}
