package diai.mule.components;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.Order;

public class StockCheck implements Callable {

	private static final String DATAFILEPATH = "src/stock.properties";

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		Order orden = (Order) eventContext.getMessage().getPayload();
		Properties dataProp = new Properties();
		InputStream dataInput = null;

		try {

			dataInput = new FileInputStream(DATAFILEPATH);

			// load a properties file
			dataProp.load(dataInput);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (dataInput != null) {
				try {
					dataInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

}
