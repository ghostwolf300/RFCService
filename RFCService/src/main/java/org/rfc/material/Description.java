package org.rfc.material;

import java.io.Serializable;

public class Description implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String language;
	private String description;
	
	public Description() {
		super();
	}
	
	public Description(String language,String description) {
		this.language=language;
		this.description=description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
