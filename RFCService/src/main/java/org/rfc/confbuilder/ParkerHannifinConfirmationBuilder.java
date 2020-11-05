package org.rfc.confbuilder;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rfc.orderconf.SupplierConfirmationDTO;
import org.rfc.orderconf.SupplierConfirmationItemDTO;

public class ParkerHannifinConfirmationBuilder extends ConfirmationBuilder {
	
	private DecimalFormat decimalFormat=null;
	private SimpleDateFormat dateFormat=null;
	
	public ParkerHannifinConfirmationBuilder() {
		super();
		decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
		DecimalFormatSymbols custom=new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		custom.setGroupingSeparator(',');
		decimalFormat.setDecimalFormatSymbols(custom);
		dateFormat = new SimpleDateFormat("dd/MM/yy");
	}
	
	@Override
	public SupplierConfirmationDTO build(String text) {
		SupplierConfirmationDTO confirmation=new SupplierConfirmationDTO();
		//System.out.println(text);
		String[] lines = text.split("\\r?\\n");
		
		for(int i=0;i<lines.length;i++) {
			System.out.println(i+"\t"+lines[i]);
		}
		
		confirmation.setCustomerOrderId(lines[3].trim());
		confirmation.setItems(getItems(lines));
		
		return confirmation;
	}
	
	private List<SupplierConfirmationItemDTO> getItems(String[] lines){
		List<SupplierConfirmationItemDTO> items=new ArrayList<SupplierConfirmationItemDTO>();
			
		//int lineStart=51;
		//int lineEnd=56;
		int lineStart=getLineStart(lines);
		int lineEnd=getLineEnd(lines,lineStart);
		for(int i=lineStart;i<lineEnd;i+=2) {
			SupplierConfirmationItemDTO item=new SupplierConfirmationItemDTO();
			String[] fields1=lines[i].split("\\s+");
			String[] fields2=lines[i+1].split("\\s+");
			//System.out.println(lines[i]);
			//System.out.println(lines[i+1]);
			item.setItemNumber((int)Double.parseDouble(fields1[0]));
			item.setOrderedQuantity(Double.parseDouble(fields1[1]));
			item.setSupplierId(fields1[2]);
			item.setUnitNetPrice(Double.parseDouble(fields1[3]));
			item.setTotalNetPrice(parseTotalNetPrice(fields1[4]));
			item.setSupplierDescription(fields2[0]);
			item.setCustomerId(fields2[1]+fields2[2]);
			item.setConfirmedDate(parseConfirmedDate(fields1[4]));
			items.add(item);
		}
		
		return items;
	}
	
	private int getLineStart(String[] lines) {
		int startLine=-1;
		for(int i=0;i<lines.length;i++) {
			if(lines[i].equals("Netto")) {
				startLine=i+1;
				return startLine;
			}
		}
		return startLine;
	}
	
	private int getLineEnd(String[] lines,int lineStart) {
		int endLine=-1;
		for(int i=lineStart;i<lines.length;i++) {
			if(lines[i].contains("Toimitusehdon ollessa vapaasti varastossanne")) {
				endLine=i-1;
				return endLine;
			}
		}
		return endLine;
	}
	
	private double parseTotalNetPrice(String text) {
		double net=0.0;
		String regex="\\d+[,.]\\d+[,.]\\d+";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(text);
		if(matcher.find()) {
			try {
				net=decimalFormat.parse(matcher.group()).doubleValue();
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return net;
	}
	
	private Date parseConfirmedDate(String text) {
		Date d=null;
		String regex="\\d+\\/\\d+\\/\\d+";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(text);
	    if(matcher.find()) {
	    	try {
				d=new Date(dateFormat.parse(matcher.group()).getTime());
			} 
	    	catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return d;
	}

}
