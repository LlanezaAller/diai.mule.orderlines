package diai.mule.entities;

import java.util.HashMap;

public class Order {

	protected Client client = new Client();
	protected Boolean financiacion;
	protected HashMap<String, StockOrder> productos = new HashMap<String, StockOrder>();
	protected Double cash;
	
	public Client getClient() {
		return client;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public HashMap<String, StockOrder> getProductos() {
		return productos;
	}

	public void setProductos(HashMap<String, StockOrder> productos) {
		this.productos = productos;
	}

	public Boolean getFinanciacion() {
		return financiacion;
	}

	public void setFinanciacion(Boolean financiacion) {
		this.financiacion = financiacion;
	}

	public void addProducto ( String string, Integer string2 )
	{
		if (!productos.containsKey(string)) {
			StockOrder newStockOrder = new StockOrder();
			
			newStockOrder.setAmount(string2);
			newStockOrder.setClient(new Client());
			newStockOrder.getClient().setNombre(this.client.getNombre());
			newStockOrder.getClient().setDni(this.client.getDni());
			newStockOrder.setIsbn(string);
			newStockOrder.setFinancia(getFinanciacion());
			
			productos.put(string, newStockOrder);
		}
	}
	
	public void addProducto(String isbn, StockOrder order) {
		if(!productos.containsValue(order))
			productos.put(order.getIsbn(), order);
	}

	@Override
	public String toString() {
		return "Orden [nombre=" + client.getNombre() + ", dni=" + client.getDni() + ", financiacion=" + financiacion + ", productos="
				+ productos + "]";
	}
}
