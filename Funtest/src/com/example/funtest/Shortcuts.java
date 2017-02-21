package com.example.funtest;

public class Shortcuts {

	private String shortcutsName;
	private int image_id;
	
	public Shortcuts(String shortcutsName,int image_id) {
		// TODO Auto-generated constructor stub
		this.setImage_id(image_id);
		this.setShortcutsName(shortcutsName);
	}

	public String getShortcutsName() {
		return shortcutsName;
	}

	public void setShortcutsName(String shortcutsName) {
		this.shortcutsName = shortcutsName;
	}

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	
	
}
