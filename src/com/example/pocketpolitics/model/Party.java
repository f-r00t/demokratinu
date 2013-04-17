package com.example.pocketpolitics.model;

import android.provider.MediaStore.Images.Thumbnails;

public class Party {

	private Thumbnails logo;
	private String name;
	
	public Thumbnails getLogo() {
		return logo;
	}
	public void setLogo(Thumbnails logo) {
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
