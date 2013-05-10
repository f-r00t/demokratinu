package org.group13.pocketpolitics.model;

/**
 * Utskott
 * @author Leif
 *
 */
public enum Committee {

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
	
	private Committee(String org){
		this.org = org;
	}
	
	public String getQueryName(){
		return org;
	}
	
	/**
	 * Iteration...
	 * @param org
	 * @return
	 */
	public static Committee findUtskott(String org){
		
		if("FöU".equals(org)){
			return Committee.Foersvar;
		}
		for(Committee u : Committee.values()){
			if(u.name().equals(org)){
				return u;
			}
		}
		
		return Committee.NULL;
	}
}
