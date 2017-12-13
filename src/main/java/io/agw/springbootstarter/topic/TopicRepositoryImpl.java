package io.agw.springbootstarter.topic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicRepositoryImpl implements TopicRepositoryCustom {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Topic> findByAVeryComplicatedQuery(String id, String name) {
		
		logger.info("findByAVeryComplicatedQuery called!");
		
		return new ArrayList<>();
	}

}
