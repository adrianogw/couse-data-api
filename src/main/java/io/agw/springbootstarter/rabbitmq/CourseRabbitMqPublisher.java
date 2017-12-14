package io.agw.springbootstarter.rabbitmq;

import org.springframework.stereotype.Component;

import io.agw.springbootstarter.course.Course;
import io.agw.springbootstarter.course.CourseDto;

@Component
public class CourseRabbitMqPublisher extends RabbitMQSender<Course>{

    private final static String TOPIC_QUEUE_NAME = "queueCourses";
    
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
	public ResourceStateMessage getResourceStateMessage(Course resource, ChangeType changeType) {
		
		return new ResourceStateMessage(CourseDto.generateUri(resource.getId()), changeType);
	}

}
