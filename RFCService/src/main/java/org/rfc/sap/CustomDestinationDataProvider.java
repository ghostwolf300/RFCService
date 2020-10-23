package org.rfc.sap;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class CustomDestinationDataProvider implements DestinationDataProvider {
	
    private DestinationDataEventListener eL;
    private Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
    
    public CustomDestinationDataProvider() {
    	super();
    }
	
	@Override
	public Properties getDestinationProperties(String destinationName) throws DataProviderException {
		return propertiesMap.get(destinationName);
	}
	
	public void setDestinationProperties(String destinationName, Properties properties) {
        propertiesMap.put(destinationName, properties);

        if(this.eL != null) {
            this.eL.updated(destinationName);
        }
    }

	@Override
	public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
		this.eL=eventListener;

	}

	@Override
	public boolean supportsEvents() {
		return true;
	}

}
