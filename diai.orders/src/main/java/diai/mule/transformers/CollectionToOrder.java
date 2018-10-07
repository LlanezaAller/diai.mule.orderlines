package diai.mule.transformers;

import java.util.Collection;
import java.util.List;

import org.mule.api.annotations.param.Payload;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class CollectionToOrder extends AbstractTransformer {

	public CollectionToOrder()
	{
		this.registerSourceType(DataTypeFactory.create(Collection.class));
		this.setReturnDataType(DataTypeFactory.create(Order.class));
	}

	private final static String DEBTPATH = "src/debt.properties";

	@Override
	protected Object doTransform(Object src, String encoding) {
		Order order = new Order();

		if (src instanceof List<?>) {
			List<StockOrder> stockOrders = (List<StockOrder>) src;

			SetOrdersFromMessages(order, stockOrders);

		}
		return order;
	}

	private void SetOrdersFromMessages(Order order, List<StockOrder> stockOrders) {
		if (stockOrders.size() > 0) {
			order.setClient(stockOrders.get(0).getClient());
			order.setFinanciacion(stockOrders.get(0).getFinancia());
		}
		stockOrders.forEach(so -> {
			if (so.getAvailable())
				order.addProducto(so.getIsbn(), so);

		});
	}
}
