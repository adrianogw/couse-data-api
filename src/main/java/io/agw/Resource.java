package io.agw;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(property = "type", use = Id.NAME, include = As.PROPERTY)
public abstract class Resource{

	private String id;
	private String name;
	private String description;
    private String type;
	
	public Resource() {
		super();
        setType();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
    @JsonIgnore
    public final String getType()
    {
        return this.type;
    }
    
    private void setType()
    {
        if (getClass().isAnnotationPresent(JsonTypeName.class))
        {
            this.type = getClass().getAnnotation(JsonTypeName.class).value();
        }
        else
        {
            this.type = getClass().getSimpleName();
        }
    }
	
	public abstract String getUri();
	
}
