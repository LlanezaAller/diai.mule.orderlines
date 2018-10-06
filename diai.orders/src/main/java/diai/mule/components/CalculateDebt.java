package diai.mule.components;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.annotations.param.Payload;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class CalculateDebt implements Callable {

	private final static String DEBTPATH = "src/debt.properties";

	@Override
	public Object onCall(@Payload MuleEventContext eventContext) throws Exception {
		System.out.println(eventContext.getMessage().getPayload());
			Order order = new Order();
			
		if (eventContext.getMessage().getPayload() instanceof List<?>) {
			List<StockOrder> stockOrders = (List<StockOrder>) eventContext.getMessage().getPayload();

			SetOrdersFromMessages(order, stockOrders);
			
			Properties dataProp = new Properties();
			InputStream dataInput = null;

			try {

				dataInput = new FileInputStream(DEBTPATH);

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
			
		}
		return order;
	}

	private void SetOrdersFromMessages(Order order, List<StockOrder> stockOrders) {
		if (stockOrders.size() > 0)
			order.setClient(stockOrders.get(0).getClient());

		stockOrders.forEach(so -> {
			if (so.getAvailable())
				order.addProducto(so.getIsbn(), so);

		});
	}

	
}
