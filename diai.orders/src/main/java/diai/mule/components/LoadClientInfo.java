package diai.mule.components;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import de.schlichtherle.io.FileOutputStream;
import diai.mule.entities.StockOrder;

public class LoadClientInfo implements Callable {

	private static final String DATAFILEPATH = "src/clientmoney.properties";
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		StockOrder order = (StockOrder) eventContext.getMessage().getPayload();
		
		Properties dataProp = new Properties();
		InputStream dataInput = null;

		try {

			dataInput = new FileInputStream(DATAFILEPATH);

			// load a properties file
			dataProp.load(dataInput);
			
			Double amount = (Double) dataProp.get(order.getClient().getDni());
			
			order.getClient().setLastMonth(amount);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (dataInput != null) {
				try {
					dataInput.close();

					FileOutputStream out = new FileOutputStream(DATAFILEPATH);
					dataProp.store(out, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return order;
	}

}
