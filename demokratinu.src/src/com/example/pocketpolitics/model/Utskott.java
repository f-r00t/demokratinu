package com.example.pocketpolitics.model;

public enum Utskott {

	Arbetsmarknad ("AU"),
	Civil("CU"),
	Finans("FiU"),
	Foersvar("F%C3%B6U"),
	Justitie("JuU"),
	Konstitution("KU"),
	Kultur("KrU"),
	MiljoJordbruk("MJU"),
	Naering("NU"),
	Skatt("SkU"),
	SocialFoersaekring("SfU"),
	Social("SoU"),
	Trafik("TU"),
	Utbildning("UbU"),
	Utrikes("UU"),
	NULL("");
	
	
	private String org;
	
	Utskott(String org){
		this.org = org;
	}
	
	public String getQueryName(){
		return org;
	}
	
}
