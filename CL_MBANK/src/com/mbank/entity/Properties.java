package com.mbank.entity;

public class Properties {
	private String prop_key;
	private String prop_value;
	public String getProp_key() {
		return prop_key;
	}
	public void setProp_key(String prop_key) {
		this.prop_key = prop_key;
	}
	public String getProp_value() {
		return prop_value;
	}
	public void setProp_value(String prop_value) {
		this.prop_value = prop_value;
	}
	public Properties(String prop_key, String prop_value) {
		this.prop_key = prop_key;
		this.prop_value = prop_value;
	}
	public Properties() {}
	
	
}
