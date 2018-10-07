package diai.mule.components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.Order;

public class DoFinance {
	
	private final static String FOLDERPATH = "finances/";
	private final static String template = "Dear %s.\r\n" + 
			"Your order finance has been accepted.";

	public void CreateMail(@Payload Order order) {
	    BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(FOLDERPATH+order.getClient().getDni()+".txt"));
			writer.append(String.format(template, order.getClient().getNombre()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
