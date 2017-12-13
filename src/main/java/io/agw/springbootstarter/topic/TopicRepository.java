/**
 * 
 */
package io.agw.springbootstarter.topic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author adrianogw
 *
 */
public interface TopicRepository extends CrudRepository<Topic, String>, TopicRepositoryCustom {
	

	@Query("select t from Topic t where t.name = :name")
	public List<Topic> findByName(@Param("name") String name);
}
