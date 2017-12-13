package io.agw.springbootstarter.topic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.agw.springbootstarter.rabbitmq.RabbitCredentials;

@RestController
public class TopicController {

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/topics")
	public List<TopicDto> getAllTopics() {
		
		List<Topic> topics = topicService.getAllTopics();
		
		List<TopicDto> topicsDtoList = new ArrayList<TopicDto>();
		
		for (Topic topic: topics) {
			topicsDtoList.add(TopicConverter.toTopicDto(topic));
		}
		
		return topicsDtoList;
	}

	@RequestMapping("/topics/{id}")
	public TopicDto getTopic(@PathVariable String id) throws Exception {
		
		Topic topic = topicService.getTopic(id);
		
		return TopicConverter.toTopicDto(topic);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/topics")
	public TopicDto addTopic(@RequestBody TopicDto topicDto) throws Exception{

		Topic topic = topicService.addTopic(TopicConverter.toTopicDb(topicDto));
		
		return TopicConverter.toTopicDto(topic);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{id}")
	public TopicDto updateTopic(@RequestBody TopicDto topicDto, @PathVariable String id) throws Exception {
		
		Topic topic = topicService.updateTopic(id, TopicConverter.toTopicDb(topicDto));
		
		return TopicConverter.toTopicDto(topic);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}")
	public void deleteTopic(@PathVariable String id) throws Exception {
		topicService.deleteTopic(id);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/topics/authRabbitMq/{authToken}")
	public RabbitCredentials getRabbitMq(@PathVariable String authToken) throws Exception {
		
		//TO-DO: pick the credentials from a properties file like Hibernate config file.
		String login = env.getProperty("rabbitMq.login");
		String passcode = env.getProperty("rabbitMq.passcode");
				
		return new RabbitCredentials(login, passcode);
	}
}
