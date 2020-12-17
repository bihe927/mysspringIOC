package com.spring.core;

import java.util.HashMap;
import java.util.Map;

public class EntityBean {
    private String id;
    private String classname;
    private Map<String,String> properties;

    @Override
    public String toString() {
        return "EntityBean{" +
                "id='" + id + '\'' +
                ", classname='" + classname + '\'' +
                ", properties=" + properties +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public EntityBean(String id, String classname, Map<String, String> properties) {
        this.id = id;
        this.classname = classname;
        this.properties = properties;
    }

    public EntityBean() {
        properties = new HashMap<String, String>();
    }
}
