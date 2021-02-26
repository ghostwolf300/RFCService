package org.rfc.material.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedLineDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat format=new SimpleDateFormat("dd.MM.yy HH:mm:ss");
	
	private Timestamp timeStamp;
	private String source;
	private String lineText;
	
	public FeedLineDTO() {
		super();
	}
	
	public FeedLineDTO(Timestamp timeStamp,String source,String lineText) {
		super();
		this.timeStamp=timeStamp;
		this.source=source;
		this.lineText=lineText;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLineText() {
		return lineText;
	}

	public void setLineText(String lineText) {
		this.lineText = lineText;
	}
	
	@JsonProperty(value="lineFeedText",access=JsonProperty.Access.READ_ONLY)
	public String getLineFeedText() {
		String ts=format.format(timeStamp);
		return ts+" "+source+"\t"+lineText;
	}
	

}
