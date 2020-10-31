package org.rfc.sap;

import org.rfc.exception.RFCException;

import com.sap.conn.jco.JCoDestination;

public interface SapService {
	
	public boolean loginUser(SapUserDTO user);
	public void logoutUser();
	public SapUserDTO getCurrentUser();
	public JCoDestination getDestination() throws RFCException;
	
}
