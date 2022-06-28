/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.eventmesh.connector.standalone.broker;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.apache.eventmesh.connector.standalone.broker.model.MessageEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

public class StandaloneBrokerTest {

    CloudEvent DEFAULT_EVENT = CloudEventBuilder.v1()
            .withId("test")
            .withSource(URI.create("testsource"))
            .withType("testType")
            .build();

    String DEFAULT_TOPIC = "test-topic";

    StandaloneBroker standaloneBroker;

    @Before
    public void beforeMethod() {
        // new instance each method, to make sure share nothing between each method
        standaloneBroker = StandaloneBroker.newInstance();
    }

    @Test
    public void testGetInstance() {
        StandaloneBroker instance1 = StandaloneBroker.getInstance();
        StandaloneBroker instance2 = StandaloneBroker.getInstance();
        Assert.assertNotNull(instance1);
        Assert.assertNotNull(instance2);
        Assert.assertEquals(instance1, instance2);
    }

    @Test
    public void testPutMessage() throws InterruptedException {
        MessageEntity messageEntity = standaloneBroker.putMessage(DEFAULT_TOPIC, DEFAULT_EVENT);
        Assert.assertNotNull(messageEntity);
    }

    @Test
    public void testTakeMessage() throws InterruptedException {
        standaloneBroker.putMessage(DEFAULT_TOPIC, DEFAULT_EVENT);
        CloudEvent message = standaloneBroker.takeMessage(DEFAULT_TOPIC);
        Assert.assertNotNull(message);
    }

    @Test
    public void testGetMessage() throws InterruptedException {
        CloudEvent message = standaloneBroker.getMessage(DEFAULT_TOPIC);
        Assert.assertNull(message);
        standaloneBroker.putMessage(DEFAULT_TOPIC, DEFAULT_EVENT);
        message = standaloneBroker.getMessage(DEFAULT_TOPIC);
        Assert.assertNotNull(message);
        Assert.assertEquals(DEFAULT_EVENT, message);
    }

    @Test
    public void testGetMessageByOffset() throws InterruptedException {
        MessageEntity messageEntity = standaloneBroker.putMessage(DEFAULT_TOPIC, DEFAULT_EVENT);
        CloudEvent message = standaloneBroker.getMessage(DEFAULT_TOPIC, messageEntity.getOffset());
        Assert.assertEquals(message, DEFAULT_EVENT);
    }

    @Test
    public void testCheckTopicExist() {
        Assert.assertFalse(standaloneBroker.checkTopicExist(DEFAULT_TOPIC));
        standaloneBroker.createTopicIfAbsent(DEFAULT_TOPIC);
        Assert.assertTrue(standaloneBroker.checkTopicExist(DEFAULT_TOPIC));
    }

}