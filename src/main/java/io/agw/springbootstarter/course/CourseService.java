/**
 * 
 */
package io.agw.springbootstarter.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author adrianogw
 *
 */

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

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
		return courseRepository.findOne(id);
	}

	public Course addCourse(Course course) {
		return courseRepository.save(course);

	}

	public Course updateCourse(Course course) {
		return courseRepository.save(course);
	}

	public void deleteCourse(String id) {
		courseRepository.delete(id);
	}
}
