/**
 * 
 */
package io.agw.springbootstarter.course;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.agw.springbootstarter.rabbitmq.ChangeType;
import io.agw.springbootstarter.rabbitmq.CourseRabbitMqPublisher;

/**
 * @author adrianogw
 *
 */

@Service
public class CourseService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository courseRepository;

	@Resource
	private CourseRabbitMqPublisher courseRabbitMqPublisher;
	
	public List<Course> getAllCourses(String topicUri) {
		List<Course> courses = new ArrayList<Course>();
		
		if (StringUtils.isEmpty(topicUri))
		{
			courseRepository.findAll().forEach(courses::add);
		}
		else
		{
			courseRepository.findByTopicUri(topicUri).forEach(courses::add);
		}
		
		return courses;
	}

	public Course getCourse(String id) {
		
		Course course = courseRepository.findOne(id);
		
		if (course == null || StringUtils.isEmpty(course.getId()))
		{
			logger.debug("Resource not found! Specified ID: "+ id);
			throw new io.agw.springbootstarter.exceptions.ResourceNotFoundException("Resource not found!",
					"Verify if the specified ID really exists!", 
					"Specified ID: "+ id);
		}
		
		return course;
	}

	public Course addCourse(Course course) throws Exception {
		
		Course savedCourse = courseRepository.save(course);
		
		courseRabbitMqPublisher.publishTopicRabbitMQ(savedCourse, ChangeType.Created);
		
		return savedCourse;
	}

	public Course updateCourse(String id, Course course) throws Exception {
		
		//Make sure the specified topic exists.
		getCourse(id);
		
		Course updatedCourse = courseRepository.save(course);
		
		courseRabbitMqPublisher.publishTopicRabbitMQ(updatedCourse, ChangeType.Updated);
		
		return updatedCourse;
	}

	public void deleteCourse(String id) throws Exception {
		
		//Make sure the specified course exists.
		Course deletedCourse = getCourse(id);
				
		courseRepository.delete(id);
		
		courseRabbitMqPublisher.publishTopicRabbitMQ(deletedCourse, ChangeType.Deleted);
		
	}
}
