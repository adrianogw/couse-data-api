package io.agw.springbootstarter.rabbitmq;

import java.io.Serializable;

public class ResourceStateMessage implements Serializable{

	private static final long serialVersionUID = -1982898764626403989L;
	
	private String resourceUri;
    private ChangeType changeType;
    
	public ResourceStateMessage(String resourceUri, ChangeType changeType) {
		super();
		this.resourceUri = resourceUri;
		this.changeType = changeType;
	}
	
	public String getResourceUri() {
		return resourceUri;
	}
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}
	public ChangeType getChangeType() {
		return changeType;
	}
	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}

    
}
