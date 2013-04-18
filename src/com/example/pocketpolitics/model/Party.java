package com.example.pocketpolitics.model;

import android.widget.ImageView;

public class Party {

	private ImageView logo;
	private String name;
	
	public Party(String name, ImageView logo) {
		this.name = name;
		this.logo = logo;
	}
	
	/**
	 * @return The logotype view of this party
	 */
	public ImageView getLogo() {
		return logo;
	}
	
	/**
	 * @param logo The logotype view of this party
	 */
	public void setLogo(ImageView logo) {
		this.logo = logo;
	}
	
	/**
	 * @return The name of this party
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name The name of this party
	 */
	public void setName(String name) {
		this.name = name;
	}
}
