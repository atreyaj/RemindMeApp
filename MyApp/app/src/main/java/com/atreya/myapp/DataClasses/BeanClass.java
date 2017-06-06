package com.atreya.myapp.DataClasses;

/**
 * Created by atreya on 19/4/17.
 */

public class BeanClass
{
    String label;

    public String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}