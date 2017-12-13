package io.agw.springbootstarter.topic;

public class TopicConverter {

	public static TopicDto toTopicDto(Topic topic) {
		
		TopicDto topicDto = new TopicDto();
		
		topicDto.setId(topic.getId());
		topicDto.setName(topic.getName());
		topicDto.setDescription(topic.getDescription());

		return topicDto;
	}
	
	public static Topic toTopicDb(TopicDto topicDto)
	{
		Topic topic = new Topic();
		
		topic.setId(topicDto.getId());
		topic.setName(topicDto.getName());
		topic.setDescription(topicDto.getDescription());

		return topic;
		
	}
}
