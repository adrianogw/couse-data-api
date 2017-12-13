/**
 * 
 */
package io.agw.springbootstarter.topic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 *  SLF4J is a façade for commonly used logging frameworks, 
 *  such as Java Util Logging, Log4J 2, and Logback. 
 *  By writing against SLF4J, our code remains decoupled from Logback, thus providing us the flexibility to plug-in a different logging framework, if required later.
 *  Source: https://springframework.guru/using-logback-spring-boot/
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Resource
	private RabbitMQSender rabbitMQSender;

	public List<Topic> getAllTopics(Boolean sortByName) {
		
		List<Topic> topics = new ArrayList<Topic>();
		
		if (Boolean.TRUE == sortByName)
		{
			topicRepository.findAllSortedByNameAsc().forEach(topics::add);
		}
		else
		{
			topicRepository.findAll().forEach(topics::add);
		}
		return topics;
	}

	public Topic getTopic(String id) throws Exception {

		Topic topic = topicRepository.findOne(id);
		
		if (topic == null || StringUtils.isEmpty(topic.getId()))
		{
			logger.debug("Resource not found! Specified ID: "+ id);
			throw new io.agw.springbootstarter.exceptions.ResourceNotFoundException("Resource not found!",
					"Verify if the specified ID really exists!", 
					"Specified ID: "+ id);
		}
		
		return topic;
	}

	public Topic getTopicByResourceUri(String resourceUri) throws Exception {

		Topic topic = topicRepository.findByResourceUri(resourceUri);
		
		if (topic == null || StringUtils.isEmpty(topic.getId()))
		{
			logger.debug("Resource not found! Specified resourceUri: "+ resourceUri);
			throw new io.agw.springbootstarter.exceptions.ResourceNotFoundException("Resource not found!",
					"Verify if the specified ID really exists!", 
					"Specified resourceUri: "+ resourceUri);
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
