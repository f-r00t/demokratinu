package com.example.pocketpolitics.model;

import android.widget.ImageView;

public class Party {

	private ImageView logo;
	private String name;
	
	public Party(String name, ImageView logo) {
		this.name = name;
		this.logo = logo;
	}
	
	public ImageView getLogo() {
		return logo;
	}
	public void setLogo(ImageView logo) {
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
