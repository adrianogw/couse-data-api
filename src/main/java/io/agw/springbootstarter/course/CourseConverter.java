package io.agw.springbootstarter.course;

import io.agw.springbootstarter.topic.TopicDto;
import io.agw.springbootstarter.topic.TopicService;

public class CourseConverter {

	public static CourseDto toCourseDto(Course course) {
		
		CourseDto courseDto = new CourseDto();
		
		courseDto.setId(course.getId());
		courseDto.setName(course.getName());
		courseDto.setDescription(course.getDescription());
		courseDto.setTopicUri(TopicDto.generateUri(course.getTopic().getId()));

		return courseDto;
	}
	
	public static Course toCourseDb(CourseDto courseDto, TopicService topicService) throws Exception
	{
		Course course = new Course();
		
		course.setId(courseDto.getId());
		course.setName(courseDto.getName());
		course.setDescription(courseDto.getDescription());

		//Retrieve the entity to satisfy the association between the classes.
		course.setTopic(topicService.getTopicByResourceUri(courseDto.getTopicUri()));
		
		return course;
		
	}
}
