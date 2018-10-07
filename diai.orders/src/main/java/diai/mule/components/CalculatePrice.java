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
	private static final String MONEYPATH = "src/clientmoney.properties";

	public Order calculate(@Payload Order order) {

		order.setCash(calculateFacture(order));

		return order;
	}

	private Double calculateFacture(Order order) {
		Double result = 0.0;
		Properties dataProp = new Properties();
		Properties moneyProp = new Properties();
		InputStream dataInput = null;
		InputStream moneyInput = null;

		try {

			dataInput = new FileInputStream(PRICESPATH);
			moneyInput = new FileInputStream(MONEYPATH);

			// load a properties file
			dataProp.load(dataInput);
			moneyProp.load(moneyInput);

			for (String key : order.getProductos().keySet()) {
				StockOrder stock = order.getProductos().get(key);
				if (stock.getAvailable()) {
					Double price = Double.parseDouble(dataProp.get(key).toString());
					stock.setPrice(price);
					result += stock.getAmount() * price;
				}
			}
			Object moneyRaw = moneyProp.get(order.getClient().getDni());

			Double moneyAmount = 0.0;

			if (moneyRaw != null) {
				moneyAmount = Double.parseDouble(moneyRaw.toString());
				moneyAmount += result;
			}

			moneyProp.setProperty(order.getClient().getDni(), moneyAmount.toString());
			order.setCash(result);

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (dataInput != null) {
				try {
					dataInput.close();
					moneyInput.close();

					FileOutputStream outData = new FileOutputStream(PRICESPATH);
					FileOutputStream outMoney = new FileOutputStream(MONEYPATH);
					dataProp.store(outData, null);
					moneyProp.store(outMoney, null);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
