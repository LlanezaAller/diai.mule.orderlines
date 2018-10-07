package diai.mule.components;

import org.mule.api.annotations.param.Payload;

import diai.mule.entities.Order;

public class RejectFinance {

	public void NotifyRecject(@Payload Order src) {
		System.out.println("Finance reject due to actual debt:::");
		System.out.println("Client: <"+src.getClient().getDni() + "> "+src.getClient().getNombre());
		System.out.println("Debt: "+src.getClient().getDebt());
	}
}
