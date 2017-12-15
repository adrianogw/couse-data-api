package io.agw.springbootstarter.topic;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.agw.Resource;

@JsonTypeName(TopicDtoV2.TYPE)
public class TopicDtoV2 extends Resource {

	public static final String TOPIC_REST_PATH = "/rest/topics"; 
	
	private static final long serialVersionUID = -8583269114322023805L;

	private String category;
	
	public static final String TYPE = "TopicDtoV2";
	
	public TopicDtoV2() {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
