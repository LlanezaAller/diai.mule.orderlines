package diai.mule.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.StockOrder;

public class ProcessOrderLine implements Callable {

	private static final String DATAFILEPATH = "src/stock.properties";

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		StockOrder order = (StockOrder) eventContext.getMessage().getPayload();
		Properties dataProp = new Properties();
		InputStream dataInput = null;

		try {

			dataInput = new FileInputStream(DATAFILEPATH);

			// load a properties file
			dataProp.load(dataInput);

			String amountProperty = dataProp.getProperty(order.getIsbn());
			if (amountProperty != null) {
				int stockAmount = Integer.parseInt(amountProperty);
				if(stockAmount >= order.getAmount()) {
					dataProp.setProperty(order.getIsbn(), ""+(stockAmount-order.getAmount()));
					order.setAvailable(true);
				}
				else {
					order.setAvailable(false);
				}
			}

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
