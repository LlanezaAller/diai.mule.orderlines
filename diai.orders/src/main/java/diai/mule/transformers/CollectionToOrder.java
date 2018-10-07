package diai.mule.transformers;

import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.annotations.param.Payload;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class CollectionToOrder{

	private final static String DEBTPATH = "src/debt.properties";

	public Object onCall(@Payload Object src) throws Exception {
		System.out.println("[[[[[[[" + src.getClass());
		Order order = new Order();

		if (src instanceof List<?>) {
			List<StockOrder> stockOrders = (List<StockOrder>) src;

			SetOrdersFromMessages(order, stockOrders);

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
