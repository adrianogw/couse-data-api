package io.agw.springbootstarter.topic;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.agw.Resource;

@JsonTypeName(TopicDto.TYPE)
public class TopicDto extends Resource {

	public static final String TOPIC_REST_PATH = "/rest/topics"; 
	
	private static final long serialVersionUID = -8583269114322023805L;

	public static final String TYPE = "TopicDto";
	
	public TopicDto() {
		super();
	}

	@Override
	public String getUri() {
		return generateUri(getId());
	}
	
	public static String generateUri(String id)
	{
		return TOPIC_REST_PATH+"/"+id;
	}
	
}
