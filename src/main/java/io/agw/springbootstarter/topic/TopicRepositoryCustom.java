package io.agw.springbootstarter.topic;

import java.util.List;

public interface TopicRepositoryCustom {

	public List<Topic> findByAVeryComplicatedQuery(String id, String name);
	
}
