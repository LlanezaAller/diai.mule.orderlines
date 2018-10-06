package diai.mule.entities;

public class StockOrder {
	
	private int amount;
	private Client client = new Client();
	private String isbn;
	private Boolean financia;
	private Boolean available;
	
	
	
	public Boolean getFinancia() {
		return financia;
	}
	public void setFinancia(Boolean financia) {
		this.financia = financia;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client dni) {
		this.client = client;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	
	
	@Override
	public String toString() {
		return "StockOrder [amount=" + amount + ", client=" + client + ", isbn=" + isbn + "]";
	}
	
	

}
