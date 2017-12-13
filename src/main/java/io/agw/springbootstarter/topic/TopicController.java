package io.agw.springbootstarter.topic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.agw.springbootstarter.rabbitmq.RabbitCredentials;

@RestController
@RequestMapping("/topics")
public class TopicController {

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping(method=RequestMethod.GET, value="")
	public List<TopicDto> getAllTopics(
			@RequestParam(required = false, defaultValue = "false") final Boolean sortByName) 
	{
		
		List<Topic> topics = topicService.getAllTopics(sortByName);
		
		List<TopicDto> topicsDtoList = new ArrayList<TopicDto>();
		
		for (Topic topic: topics) {
			topicsDtoList.add(TopicConverter.toTopicDto(topic));
		}
		
		return topicsDtoList;
	}

	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public TopicDto getTopic(@PathVariable String id) throws Exception {
		
		Topic topic = topicService.getTopic(id);
		
		return TopicConverter.toTopicDto(topic);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="")
	public TopicDto addTopic(@RequestBody TopicDto topicDto) throws Exception{

		Topic topic = topicService.addTopic(TopicConverter.toTopicDb(topicDto));
		
		return TopicConverter.toTopicDto(topic);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public TopicDto updateTopic(@RequestBody TopicDto topicDto, @PathVariable String id) throws Exception {
		
		Topic topic = topicService.updateTopic(id, TopicConverter.toTopicDb(topicDto));
		
		return TopicConverter.toTopicDto(topic);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public void deleteTopic(@PathVariable String id) throws Exception {
		topicService.deleteTopic(id);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/authRabbitMq/{authToken}")
	public RabbitCredentials getRabbitMq(@PathVariable String authToken) throws Exception {
		
		//TO-DO: pick the credentials from a properties file like Hibernate config file.
		String login = env.getProperty("rabbitMq.login");
		String passcode = env.getProperty("rabbitMq.passcode");
				
		return new RabbitCredentials(login, passcode);
	}
}
