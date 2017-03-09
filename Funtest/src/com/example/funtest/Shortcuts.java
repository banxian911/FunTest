package com.example.funtest;

import android.graphics.drawable.Drawable;

public class Shortcuts {

	private int ID_state;
	private String shortcutsName;
	private Drawable icon_Dr;
	private String pakeName;
	
	public Shortcuts(int ID_state,String shortcutsName,String pakeName,Drawable icon_Dr) {
		// TODO Auto-generated constructor stub
		this.setID_state(ID_state);
		this.setIcon_Dr(icon_Dr);
		this.setShortcutsName(shortcutsName);
		this.setPakeName(pakeName);
	}
	
	public Shortcuts(String shortcutsName,String pakeName,Drawable icon_Dr){
		this.setIcon_Dr(icon_Dr);
		this.setShortcutsName(shortcutsName);
		this.setPakeName(pakeName);
	}

	public String getShortcutsName() {
		return shortcutsName;
	}

	public void setShortcutsName(String shortcutsName) {
		this.shortcutsName = shortcutsName;
	}


	public String getPakeName() {
		return pakeName;
	}

	public void setPakeName(String pakeName) {
		this.pakeName = pakeName;
	}

	public Drawable getIcon_Dr() {
		return icon_Dr;
	}

	public void setIcon_Dr(Drawable icon_Dr) {
		this.icon_Dr = icon_Dr;
	}

	public int getID_state() {
		return ID_state;
	}

	public void setID_state(int iD_state) {
		ID_state = iD_state;
	}
	
	
}
