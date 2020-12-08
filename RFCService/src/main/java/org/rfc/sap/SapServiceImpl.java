package org.rfc.sap;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.exception.RFCException;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

@Service("sapService")
public class SapServiceImpl implements SapService {
	
	private static final Logger logger = LogManager.getLogger(SapServiceImpl.class);
	
	private static CustomDestinationDataProvider dataProvider=null;
	
	private JCoDestination destination;
	
	@Override
	public boolean loginUser(SapUserDTO user) throws RFCException {
		
		Properties connProp=this.getConnectionProperties(user);
		String destinationName=user.getUserName()+"_"+user.getClient();
		
		if(Environment.isDestinationDataProviderRegistered()==false || dataProvider==null) {
			dataProvider=new CustomDestinationDataProvider();
			try{
				Environment.registerDestinationDataProvider(dataProvider);
			}
			catch(IllegalStateException e){
				logger.warn("Data provider is already registered.");
				e.printStackTrace();
			}
		}
		else {
			logger.warn("Data provider is already registered.");
		}
		
		
		logger.info("SAP destination: "+destinationName+", checking connection...");
		dataProvider.setDestinationProperties(destinationName, connProp);
		
		try {
			destination=JCoDestinationManager.getDestination(destinationName);
			destination.ping();
		} 
		catch (JCoException e) {
			e.printStackTrace();
			throw new RFCException(95,"SAP system not reachable!",e);
		}

		return true;
	}

	@Override
	public SapUserDTO getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Properties getConnectionProperties(SapUserDTO user) {
		Properties connProp=new Properties();
		if(user.getClient().equals("TET")) {
			connProp.setProperty(DestinationDataProvider.JCO_MSHOST, "tet");
			connProp.setProperty(DestinationDataProvider.JCO_MSSERV, "3601");
			connProp.setProperty(DestinationDataProvider.JCO_GROUP, "TET");
			connProp.setProperty(DestinationDataProvider.JCO_CLIENT, "280");
			connProp.setProperty(DestinationDataProvider.JCO_SAPROUTER, "/H/tet");
			connProp.setProperty(DestinationDataProvider.JCO_USER,   user.getUserName());
			connProp.setProperty(DestinationDataProvider.JCO_PASSWD, user.getPassword());
			connProp.setProperty(DestinationDataProvider.JCO_LANG,   "en");
		}
		else if(user.getClient().equals("TEP")) {
			connProp.setProperty(DestinationDataProvider.JCO_MSHOST, "tep");
			connProp.setProperty(DestinationDataProvider.JCO_MSSERV, "3601");
			connProp.setProperty(DestinationDataProvider.JCO_GROUP, "TEP");
			connProp.setProperty(DestinationDataProvider.JCO_CLIENT, "280");
			connProp.setProperty(DestinationDataProvider.JCO_SAPROUTER, "/H/tep");
			connProp.setProperty(DestinationDataProvider.JCO_USER,   user.getUserName());
			connProp.setProperty(DestinationDataProvider.JCO_PASSWD, user.getPassword());
			connProp.setProperty(DestinationDataProvider.JCO_LANG,   "en");
		}
		else if(user.getClient().equals("WT2")) {
			connProp.setProperty(DestinationDataProvider.JCO_MSHOST, "wt2");
			connProp.setProperty(DestinationDataProvider.JCO_MSSERV, "3601");
			connProp.setProperty(DestinationDataProvider.JCO_GROUP, "WT2");
			connProp.setProperty(DestinationDataProvider.JCO_CLIENT, "280");
			connProp.setProperty(DestinationDataProvider.JCO_SAPROUTER, "/H/wt2");
			connProp.setProperty(DestinationDataProvider.JCO_USER,   user.getUserName());
			connProp.setProperty(DestinationDataProvider.JCO_PASSWD, user.getPassword());
			connProp.setProperty(DestinationDataProvider.JCO_LANG,   "en");
		}
		else if(user.getClient().equals("WP2")) {
			connProp.setProperty(DestinationDataProvider.JCO_MSHOST, "wp2");
			connProp.setProperty(DestinationDataProvider.JCO_MSSERV, "3601");
			connProp.setProperty(DestinationDataProvider.JCO_GROUP, "WP2");
			connProp.setProperty(DestinationDataProvider.JCO_CLIENT, "280");
			connProp.setProperty(DestinationDataProvider.JCO_SAPROUTER, "/H/wp2");
			connProp.setProperty(DestinationDataProvider.JCO_USER,   user.getUserName());
			connProp.setProperty(DestinationDataProvider.JCO_PASSWD, user.getPassword());
			connProp.setProperty(DestinationDataProvider.JCO_LANG,   "en");
		}
		return connProp;
	}

	@Override
	public void logoutUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JCoDestination getDestination() throws RFCException {
		if(destination==null) {
			throw new RFCException(90,"No SAP system!");
		}
		else if(destinationIsAlive()==false) {
			throw new RFCException(95,"SAP system not reachable!");
		}
		return destination;
	}
	
	private boolean destinationIsAlive() {
		try {
			destination.ping();
		} 
		catch (JCoException e) {
			return false;
		}
		return true;
	}
	
}
