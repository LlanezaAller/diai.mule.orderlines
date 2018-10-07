package diai.mule.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class LoadClientInfo implements Callable {

	private static final String DATAFILEPATH = "src/clientmoney.properties";
	private final static String DEBTPATH = "src/debt.properties";
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		Order order = (Order) eventContext.getMessage().getPayload();
		
		Properties dataProp = new Properties();
		Properties debtProp = new Properties();
		InputStream dataInput = null;
		InputStream debtInput = null;

		try {

			dataInput = new FileInputStream(DATAFILEPATH);
			debtInput = new FileInputStream(DEBTPATH);

			// load a properties file
			dataProp.load(dataInput);
			debtProp.load(debtInput);
			
			Object cash = dataProp.get(order.getClient().getDni());
			Object debtRAW = debtProp.get(order.getClient().getDni());
			Double amount = 0.0;
			Double debt = 0.0;
			
			if(debtRAW != null)
				debt = Double.parseDouble(debtRAW.toString());
			else
				debtProp.setProperty(order.getClient().getDni(), debt.toString());
			
			if(cash!=null)
				amount = Double.parseDouble(cash.toString());
			else
				dataProp.setProperty(order.getClient().getDni(), amount.toString());
			
			order.getClient().setDebt(debt);
			order.getClient().setLastMonth(amount);
			
			for (StockOrder stock : order.getProductos().values()) {
				stock.getClient().setDebt(order.getClient().getDebt());
				stock.getClient().setLastMonth(order.getClient().getLastMonth());
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}finally {
			if (dataInput != null) {
				try {
					dataInput.close();
					debtInput.close();

					FileOutputStream outData = new FileOutputStream(DATAFILEPATH);
					FileOutputStream outDebt = new FileOutputStream(DEBTPATH);
					
					dataProp.store(outData, null);
					debtProp.store(outDebt, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return order;
	}
	
	

}
