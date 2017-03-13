package com.example.funtest;

import android.graphics.drawable.Drawable;

public class Shortcuts{

	private int ID_state;
	private String shortcutsName;
	private Drawable icon_Dr;
	private int ID_icon;
	private String pakeName;
	private String extras;
	
	public Shortcuts() {
		// TODO Auto-generated constructor stub
	}
	
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
	
	public Shortcuts(String shortcutsName,String pakeName,int ID_icon){
		this.setShortcutsName(shortcutsName);
		this.setPakeName(pakeName);
		this.setID_icon(ID_icon);
	}
	
	public Shortcuts(String shortcutsName,String pakeName,int ID_icon,String extras){
		this.setShortcutsName(shortcutsName);
		this.setPakeName(pakeName);
		this.setID_icon(ID_icon);
		this.setExtras(extras);
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

	public int getID_icon() {
		return ID_icon;
	}

	public void setID_icon(int iD_icon) {
		ID_icon = iD_icon;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}
	
	
}
