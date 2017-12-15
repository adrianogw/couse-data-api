package io.agw.springbootstarter.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.agw.springbootstarter.topic.TopicService;

@RestController
@RequestMapping("/rest/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private TopicService topicService;
	
	private static final String API_VERSION_V1 = "application/vnd.agw.api.v1+json"; 
	
	@RequestMapping(method = RequestMethod.GET, value = "", produces = API_VERSION_V1)
	public List<CourseDto> getAllCourses(
		@RequestParam(required = false, defaultValue = "") final String topicUri)
	{
		
		List<Course> courses = courseService.getAllCourses(topicUri);
		                                                   
		List<CourseDto> coursesDtoList = new ArrayList<CourseDto>();
		
		for (Course course: courses) {
			coursesDtoList.add(CourseConverter.toCourseDto(course));
		}
		
		return coursesDtoList;
	}

	@RequestMapping(method = RequestMethod.GET, value="/{id}", produces = API_VERSION_V1)
	public CourseDto getCourse(@PathVariable String id) {
		
		Course course = courseService.getCourse(id);
		
		return CourseConverter.toCourseDto(course);
	}

	@RequestMapping(method = RequestMethod.POST, value = "", produces = API_VERSION_V1)
	public CourseDto addCourse(@RequestBody CourseDto courseDto) throws Exception {
		
		Course courseSaved = courseService.addCourse(CourseConverter.toCourseDb(courseDto, topicService));
		
		return CourseConverter.toCourseDto(courseSaved);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", produces = API_VERSION_V1)
	public CourseDto updateCourse(@RequestBody CourseDto courseDto, @PathVariable String id) throws Exception {
		
		Course courseUpdated = courseService.updateCourse(id, CourseConverter.toCourseDb(courseDto, topicService));
		
		return CourseConverter.toCourseDto(courseUpdated);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = API_VERSION_V1)
	public void deleteCourse(@PathVariable String id) throws Exception {
		
		courseService.deleteCourse(id);
	}

}
