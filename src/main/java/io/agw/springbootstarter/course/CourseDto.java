package io.agw.springbootstarter.course;

import io.agw.Resource;

public class CourseDto extends Resource{

	public static final String COURSE_REST_PATH = "/courses"; 
	
	private String topicUri;
	
	public String getTopicUri() {
		return topicUri;
	}

	public void setTopicUri(String topicUri) {
		this.topicUri = topicUri;
	}

	@Override
	public String getUri() {
		return generateUri(getId());
	}
	
	public static String generateUri(String id)
	{
		return COURSE_REST_PATH+"/"+id;
	}	
}
