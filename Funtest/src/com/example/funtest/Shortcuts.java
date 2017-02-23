package com.example.funtest;

import android.graphics.drawable.Drawable;

public class Shortcuts {

	private String shortcutsName;
	private Drawable icon_Dr;
	private String pakeName;
	
	public Shortcuts(String shortcutsName,String pakeName,Drawable icon_Dr) {
		// TODO Auto-generated constructor stub
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
	
	
}
