package com.beanClass;

public class DashboardCard {

    private String id;
    private Object value;

    public DashboardCard(String id, Object value) {
        this.id = id;
        this.value = value;
    }

    public String getId() { return id; }
    public Object getValue() { return value; }
}
