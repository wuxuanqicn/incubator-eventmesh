package org.apache.eventmesh.connector.standalone.broker;

import org.apache.eventmesh.connector.standalone.broker.model.MessageEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageQueueTest {

    MessageQueue messageQueue;

    @Before
    public void beforeMethod() {
        messageQueue = new MessageQueue();
    }

    @Test
    public void testNewMessageQueue() {
        new MessageQueue();
        new MessageQueue(1);
        try {
            new MessageQueue(-1);
            Assert.fail();
        } catch (IllegalArgumentException ignore) {

        }
    }

    @Test
    public void testPutNull() {

    }

}
