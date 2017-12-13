package io.agw.springbootstarter.topic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicRepositoryCustomImpl implements TopicRepositoryCustom {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Topic> findAllSortedByNameAsc() {
		
		logger.info("findAllOrderedByNameAsc called!");
		
		return null;
	}

}
