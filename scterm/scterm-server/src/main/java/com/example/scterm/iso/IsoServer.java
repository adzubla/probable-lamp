package com.example.scterm.iso;

import com.github.kpavlov.jreactive8583.server.Iso8583Server;
import com.github.kpavlov.jreactive8583.server.ServerConfiguration;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class IsoServer {
    private static Logger LOG = LoggerFactory.getLogger(IsoServer.class);

    private Iso8583Server<IsoMessage> server;

    @Autowired
    private IsoListener listener;

    @Autowired
    private MessageFactory<IsoMessage> messageFactory;

    @PostConstruct
    public void init() throws InterruptedException {

        ServerConfiguration config = ServerConfiguration.newBuilder()
                .idleTimeout(Integer.MAX_VALUE)
                .build();

        server = new Iso8583Server<>(7777, config, messageFactory);

        server.addMessageListener(listener);
        server.init();

        LOG.info("Start...");
        server.start();
        Thread.sleep(1000);
        if (!server.isStarted()) {
            throw new IllegalStateException("Server not started");
        }
        LOG.info("Up...");
    }

    @PreDestroy
    public void shutdown() {
        LOG.info("Shutdown...");
        server.shutdown();
    }

}
