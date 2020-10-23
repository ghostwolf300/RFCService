package org.rfc.sap;

import java.util.Properties;

import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

@Service("sapService")
public class SapServiceImpl implements SapService {
	
	private JCoDestination destination;
	
	@Override
	public boolean loginUser(SapUserDTO user) {
		
		Properties connProp=this.getConnectionProperties(user);
		String destinationName=user.getUserName()+"_"+user.getClient();
		CustomDestinationDataProvider dataProvider=new CustomDestinationDataProvider();
		
		try{
			com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(dataProvider);
		}
		catch(IllegalStateException e){
			//already registered...
			System.out.println("Data provider already registered");
		}
		System.out.println("Setting properties...");
		dataProvider.setDestinationProperties(destinationName, connProp);
		
		try {
			System.out.println("Ping destination");
			destination=JCoDestinationManager.getDestination(destinationName);
			destination.ping();
		} 
		catch (JCoException e) {
			System.out.println("Ping failed. Unregistering data provider.");
			
			return false;
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
		return connProp;
	}

	@Override
	public void logoutUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JCoDestination getDestination() {
		return destination;
	}

}
