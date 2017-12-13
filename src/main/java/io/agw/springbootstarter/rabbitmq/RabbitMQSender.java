package io.agw.springbootstarter.rabbitmq;

import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import io.agw.springbootstarter.topic.Topic;
import io.agw.springbootstarter.topic.TopicDto;

@Component
public class RabbitMQSender {

	  private final static String TOPIC_QUEUE_NAME = "queueTopics";
	  private final static String EXCHANGE_NAME = "amq.topic";

	  public void publishTopicRabbitMQ(Topic topic, ChangeType changeType) throws Exception {
		  
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(TOPIC_QUEUE_NAME, true, false, false, null);

	    ResourceStateMessage resourceStateMessage = new ResourceStateMessage(TopicDto.generateUri(topic.getId()), changeType);
	    
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(resourceStateMessage);
        
	    byte[] message = json.getBytes();

        channel.basicPublish(EXCHANGE_NAME, TOPIC_QUEUE_NAME, null, message);
	    
	    System.out.println(" [x] Sent '" + json + "'");

	    channel.close();
	    connection.close();
	  }
}


