/**
 * 
 */
package io.agw.springbootstarter.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author adrianogw
 *
 */
public interface CourseRepository extends CrudRepository<Course, String> {
	
	public List<Course> findByName(String name);
	
	public List<Course> findByDescription(String description);
	
	@Query("select c from Course c where concat('/topics/',c.topic.id) = :topicUri")
	public List<Course> findByTopicUri(@Param("topicUri") String topicUri);

}
