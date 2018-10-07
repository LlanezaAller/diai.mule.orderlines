package diai.mule.components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.StockOrder;

public class RejectOrder  {
	
	private final static String WAITFOLDER = "wait/";
	private final static String template = "WaitList:\n Book %s: %d";
	
	public Object onCall(@Payload StockOrder stock) throws Exception {
		StockOrder order = (StockOrder) stock;
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(WAITFOLDER+order.getIsbn()+".txt"));
			String sheet = createWait(order);
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
		
		return order;
	}

	private String createWait(StockOrder order) {
		String result = "";
		result += String.format(template, order.getIsbn(), order.getAmount());
		
		return result;
	}

}
