/**
 * 
 */
package io.javabrains.springbootstarter.topic;

import org.springframework.data.repository.CrudRepository;

/**
 * @author rodrigo
 *
 */
public interface TopicRepository extends CrudRepository<Topic, String> {
	

}
