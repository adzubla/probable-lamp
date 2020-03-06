package com.example.scterm.iso;

import com.example.scterm.jms.ReplyToHolder;
import com.github.kpavlov.jreactive8583.IsoMessageListener;
import com.solab.iso8583.IsoMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

@Service
public class IsoListener implements IsoMessageListener<IsoMessage> {
    private static Logger LOG = LoggerFactory.getLogger(IsoListener.class);

    @Autowired
    private ConnectionManager connectionManager;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    ReplyToHolder replyToHolder;

    @Autowired
    ExecutorService executorService;

    @Override
    public boolean applies(IsoMessage isoMessage) {
        LOG.trace("applies: {}", isoMessage);
        return isoMessage.getType() == 0x200;
    }

    @Override
    public boolean onMessage(ChannelHandlerContext channelHandlerContext, IsoMessage isoMessage) {
        LOG.trace("onMessage: {} {}", channelHandlerContext, isoMessage);
        LOG.debug("Received from client: {}", isoMessage.getField(41).getValue());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                boolean ok = isMessageValid(isoMessage);
                if (ok) {
                    dispatch(channelHandlerContext, isoMessage);
                }
            }
        });

        return false;
    }

    private boolean isMessageValid(IsoMessage isoMessage) {
        return true;
    }

    private void dispatch(ChannelHandlerContext channelHandlerContext, IsoMessage isoMessage) {
        ConnectionId id = getConnectionId(isoMessage);

        connectionManager.add(id, channelHandlerContext);

        jmsTemplate.send("DEV.QUEUE.1", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String s = new String(isoMessage.writeData());

                LOG.debug("Sending to queue: {}", s);
                TextMessage message = session.createTextMessage(s);
                message.setJMSReplyTo(replyToHolder.getReplyToQueue());
                message.setJMSCorrelationID(id.getId());

                return message;
            }
        });
    }

    private ConnectionId getConnectionId(IsoMessage isoMessage) {
        Scanner scanner = new Scanner((String) isoMessage.getField(41).getValue());
        return new ConnectionId(scanner.next());
    }

}
