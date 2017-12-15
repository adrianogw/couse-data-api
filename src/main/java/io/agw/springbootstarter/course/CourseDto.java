package io.agw.springbootstarter.course;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.agw.Resource;

@JsonTypeName(CourseDto.TYPE)
public class CourseDto extends Resource{

	public static final String COURSE_REST_PATH = "/rest/courses"; 
	
	public static final String TYPE = "CourseDto";
	
	private String topicUri;
	
	public CourseDto() {
		super();
	}

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
