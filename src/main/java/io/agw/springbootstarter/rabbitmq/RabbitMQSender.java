package io.agw.springbootstarter.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class RabbitMQSender<K> {

	  public void publishTopicRabbitMQ(K resource, ChangeType changeType) throws Exception {
		  
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(getQueueName(), true, false, false, null);

        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(getResourceStateMessage(resource, changeType));
        
	    byte[] message = json.getBytes();

	    //Message will leave only 20 seconds. It does not make sense to make the UI consume old messages when started.
	    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().expiration("20000").build();
	    
        channel.basicPublish(getExchangeName(), getQueueName(), properties, message);
	    
	    System.out.println(" [x] Sent '" + json + "'");

	    channel.close();
	    connection.close();
	  }
	  
	  public abstract String getQueueName();
	  
	  public abstract String getExchangeName();
	  
	  public abstract ResourceStateMessage getResourceStateMessage(K resource, ChangeType changeType);
}


