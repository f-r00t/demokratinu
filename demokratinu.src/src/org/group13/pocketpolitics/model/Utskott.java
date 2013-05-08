package org.group13.pocketpolitics.model;

public enum Utskott {

	Arbetsmarknad ("AU"),
	Civil("CU"),
	Finans("FiU"),
	Foersvar("F%C3%B6U"),
	Justitie("JuU"),
	Konstitution("KU"),
	Kultur("KrU"),
	MiljoeJordbruk("MJU"),
	Naering("NU"),
	Skatt("SkU"),
	SocialFoersaekring("SfU"),
	Social("SoU"),
	Trafik("TU"),
	Utbildning("UbU"),
	Utrikes("UU"),
	NULL("");
	
	private String org;
	
	private Utskott(String org){
		this.org = org;
	}
	
	public String getQueryName(){
		return org;
	}
	
	/**
	 * Dålig lösning? Kanske onödig iteration... får fixa här sen
	 * TODO
	 * @param org
	 * @return
	 */
	public static Utskott findUtskott(String org){
		int FIXA;
		
		if("FöU".equals(org)){
			return Utskott.Foersvar;
		}
		for(Utskott u : Utskott.values()){
			if(u.name().equals(org)){
				return u;
			}
		}
		
		return Utskott.NULL;
	}
}
