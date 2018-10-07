package diai.mule.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.Order;

public class CalculateDebt {

	private final static String DEBTPATH = "src/debt.properties";

	public Object calculate(@Payload Order order) throws Exception {

		Properties dataProp = new Properties();
		InputStream dataInput = null;

		try {

			dataInput = new FileInputStream(DEBTPATH);

			// load a properties file
			dataProp.load(dataInput);

			Double actualDebt = 0.0;

			Object debtRAW = dataProp.get(order.getClient().getDni());
			if (debtRAW != null) {
				actualDebt += Double.parseDouble(debtRAW.toString()) + order.getCash();

				dataProp.setProperty(order.getClient().getDni(), actualDebt.toString());
			} else {
				dataProp.setProperty(order.getClient().getDni(), actualDebt.toString());
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
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

		return order;
	}

}
