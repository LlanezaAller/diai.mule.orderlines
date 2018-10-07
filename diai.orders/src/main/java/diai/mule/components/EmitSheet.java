package diai.mule.components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.Order;
import diai.mule.entities.StockOrder;

public class EmitSheet {
	
	private final static String SHEETSFOLDER = "sheets/";
	private final static String template = "BooksSheet:\n%s \nTOTAL: %s";

	public void CreateMail(@Payload Order order) {
	    BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(SHEETSFOLDER+order.getClient().getDni()+".txt"));
			String sheet = String.format(template, createProduct(order), order.getCash());
			writer.append(sheet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String createProduct(Order order) {
		String result = "";
		for (StockOrder stock : order.getProductos().values()) {
			result+=stock.getIsbn()+": "+(stock.getAmount()*stock.getPrice())+" €\n";
		}
		
		return result;
	}

}
