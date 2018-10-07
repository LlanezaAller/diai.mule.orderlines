package diai.mule.components;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class GenerateVipSend {
	
	private final static String template = "Boletín de envio VIP:\nCliente: %s\n%sTOTAL: %f";
	
	public String generateVIPSend(@Payload Order order) {

		String result = String.format(template, order.getClient().getNombre(), createProductReport(order), order.getCash());
		System.out.println(result);
		return result;
	}

	private Object createProductReport(Order order) {
		String result = "";
		
		for (StockOrder stock : order.getProductos().values()) {
			if(stock.getAvailable())
				result += stock.getIsbn() + ": " + stock.getPrice() + "(x" + stock.getAmount() + ")\n";
		}
		
		return result;
	}
}
