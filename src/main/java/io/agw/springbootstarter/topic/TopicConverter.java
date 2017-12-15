package io.agw.springbootstarter.topic;

public class TopicConverter {

	public static TopicDto toTopicDto(Topic topic) {
		
		TopicDto topicDto = new TopicDto();
		
		topicDto.setId(topic.getId());
		topicDto.setName(topic.getName());
		topicDto.setDescription(topic.getDescription());

		return topicDto;
	}
	
	public static TopicDtoV2 toTopicDtoV2(Topic topic) {
		
		TopicDtoV2 topicDtoV2 = new TopicDtoV2();
		
		topicDtoV2.setId(topic.getId());
		topicDtoV2.setName(topic.getName());
		topicDtoV2.setDescription(topic.getDescription());
		topicDtoV2.setCategory(topic.getCategory());

		return topicDtoV2;
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
