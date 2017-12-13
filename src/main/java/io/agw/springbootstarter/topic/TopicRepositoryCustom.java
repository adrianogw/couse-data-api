package io.agw.springbootstarter.topic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface TopicRepositoryCustom {

	@Query("select t from Topic t order by t.name")
	public List<Topic> findAllSortedByNameAsc();

}
