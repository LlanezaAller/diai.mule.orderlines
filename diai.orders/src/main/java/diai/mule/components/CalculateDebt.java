package diai.mule.components;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.annotations.param.Payload;
import org.mule.api.lifecycle.Callable;

import de.schlichtherle.io.FileOutputStream;
import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class CalculateDebt implements Callable {

	private final static String DEBTPATH = "src/debt.properties";

	@Override
	public Object onCall(@Payload MuleEventContext eventContext) throws Exception {
		System.out.println(eventContext.getMessage().getPayload());
		Order order = new Order();
			
		if (eventContext.getMessage().getPayload() instanceof Order) {
			order = (Order) eventContext.getMessage().getPayload();
			
			Properties dataProp = new Properties();
			InputStream dataInput = null;

			try {

				dataInput = new FileInputStream(DEBTPATH);

				// load a properties file
				dataProp.load(dataInput);
				
				
				Object debtRAW = dataProp.get(order.getClient().getDni());
				if(debtRAW != null) {
					Double actualDebt = (Double) debtRAW;
					actualDebt += calculateFacture(order);
					dataProp.setProperty(order.getClient().getDni(), actualDebt.toString());
				}else {
					
				}
				

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (dataInput != null) {
					try {
						dataInput.close();
						
						FileOutputStream out = new FileOutputStream(DEBTPATH);
						dataProp.store(out, null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		return order;
	}
	
	private Double calculateFacture(Order order) {
		
		return null;
	}

	
}
