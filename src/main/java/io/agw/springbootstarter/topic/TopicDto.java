package io.agw.springbootstarter.topic;

import io.agw.Resource;

public class TopicDto extends Resource {

	public static final String TOPIC_REST_PATH = "/topics"; 
	
	private static final long serialVersionUID = -8583269114322023805L;

	@Override
	public String getUri() {
		return generateUri(getId());
	}
	
	public static String generateUri(String id)
	{
		return TOPIC_REST_PATH+"/"+id;
	}
	
}
