package diai.mule.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.annotations.param.Payload;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class CalculateDebt implements Callable {

	private final static String DEBTPATH = "src/debt.properties";
	private final static String PRICESPATH = "src/prices.properties";

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

				Double actualDebt = 0.0;

				Object debtRAW = dataProp.get(order.getClient().getDni());
				if (debtRAW != null) {
					actualDebt += calculateFacture(order);
					actualDebt = (Double) debtRAW;
					dataProp.setProperty(order.getClient().getDni(), actualDebt.toString());
				} else {
					dataProp.setProperty(order.getClient().getDni(), actualDebt.toString());
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
		Double result = 0.0;
		Properties dataProp = new Properties();
		InputStream dataInput = null;

		try {

			dataInput = new FileInputStream(PRICESPATH);

			// load a properties file
			dataProp.load(dataInput);

			for (String key : order.getProductos().keySet()) {
				StockOrder stock = order.getProductos().get(key);
				if (stock.getAvailable()) {
					Double price = (Double) dataProp.get(key);
					result += stock.getAmount() * price;
				}
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
		return result;
	}

}
