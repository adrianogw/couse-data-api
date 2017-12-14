package io.agw.springbootstarter.rabbitmq;

import org.springframework.stereotype.Component;

import io.agw.springbootstarter.topic.Topic;
import io.agw.springbootstarter.topic.TopicDto;

@Component
public class TopicRabbitMqPublisher extends RabbitMQSender<Topic>{

    private final static String TOPIC_QUEUE_NAME = "queueTopics";
	
    //This "topic" info in the exchange is part of the configuration. It has nothing to do with the application Topic context 
    private final static String EXCHANGE_NAME = "amq.topic";

	@Override
	public String getQueueName() {
		return TOPIC_QUEUE_NAME;
	}

	@Override
	public String getExchangeName() {
		return EXCHANGE_NAME;
	}

	@Override
	public ResourceStateMessage getResourceStateMessage(Topic resource, ChangeType changeType) {
		
		return new ResourceStateMessage(TopicDto.generateUri(resource.getId()), changeType);
	}

}
