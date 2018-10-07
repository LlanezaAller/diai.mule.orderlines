package diai.mule.transformers;

import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.annotations.param.Payload;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class CollectionToOrder implements Callable {

	private final static String DEBTPATH = "src/debt.properties";

	@Override
	public Object onCall(@Payload MuleEventContext eventContext) throws Exception {
		System.out.println(eventContext.getMessage().getPayload());
		Order order = new Order();

		if (eventContext.getMessage().getPayload() instanceof List<?>) {
			List<StockOrder> stockOrders = (List<StockOrder>) eventContext.getMessage().getPayload();

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
