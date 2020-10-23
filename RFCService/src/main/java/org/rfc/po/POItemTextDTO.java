package org.rfc.po;

import java.io.Serializable;

public class POItemTextDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int poId;
	private int item;
	private String textId;
	private String textForm;
	private String textLine;
	
	public POItemTextDTO() {
		super();
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public String getTextId() {
		return textId;
	}

	public void setTextId(String textId) {
		this.textId = textId;
	}

	public String getTextForm() {
		return textForm;
	}

	public void setTextForm(String textForm) {
		this.textForm = textForm;
	}

	public String getTextLine() {
		return textLine;
	}

	public void setTextLine(String textLine) {
		this.textLine = textLine;
	}

}
