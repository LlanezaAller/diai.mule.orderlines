package diai.mule.components;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class GenerateSend {
	
	private final static String template = "Bolet�n de env�o comun:\nCliente: %s\n%sTOTAL: %f";
	
	public String generateSend(@Payload Order order) {

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
