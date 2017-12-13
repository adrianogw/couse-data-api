/**
 * 
 */
package io.agw.springbootstarter.topic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.agw.springbootstarter.rabbitmq.ChangeType;
import io.agw.springbootstarter.rabbitmq.RabbitMQSender;

/**
 * @author adrianogw
 *
 */

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	@Resource
	private RabbitMQSender rabbitMQSender;

	public List<Topic> getAllTopics() {
		
		List<Topic> topics = new ArrayList<Topic>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}

	public Topic getTopic(String id) throws Exception {

		Topic topic = topicRepository.findOne(id);
		
		if (topic == null || StringUtils.isEmpty(topic.getId()))
		{
			throw new io.agw.springbootstarter.exceptions.ResourceNotFoundException("Resource not found!",
					"Verify if the specified ID really exists!", 
					"Specified ID: "+ id);
		}
		
		return topic;
	}

	public Topic addTopic(Topic topic) throws Exception{
		
		Topic savedTopic = topicRepository.save(topic);
		
		rabbitMQSender.publishTopicRabbitMQ(savedTopic, ChangeType.Created);

		return savedTopic; 
	}

	public Topic updateTopic(String id, Topic topic) throws Exception{
		
		//Make sure the specified topic exists.
		getTopic(id);

		Topic updatedTopic = topicRepository.save(topic);
		
		rabbitMQSender.publishTopicRabbitMQ(updatedTopic, ChangeType.Updated);
		
		return updatedTopic;
	}

	public void deleteTopic(String id) throws Exception {
		
		//Make sure the specified topic exists.
		Topic deletedTopic = getTopic(id);

		topicRepository.delete(id);
		
		rabbitMQSender.publishTopicRabbitMQ(deletedTopic, ChangeType.Deleted);
	}
	
}
