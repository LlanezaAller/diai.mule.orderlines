package diai.mule.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class CalculatePrice {
	private final static String PRICESPATH = "src/prices.properties";

	public Order calculate(@Payload Order order) {
		
		order.setCash(calculateFacture(order));
		
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
					Double price = Double.parseDouble(dataProp.get(key).toString());
					stock.setPrice(price);
					result += stock.getAmount() * price;
				}
			}
			order.setCash(result);

		} catch (IOException ex) {
			ex.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if (dataInput != null) {
				try {
					dataInput.close();

					FileOutputStream out = new FileOutputStream(PRICESPATH);
					dataProp.store(out, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
