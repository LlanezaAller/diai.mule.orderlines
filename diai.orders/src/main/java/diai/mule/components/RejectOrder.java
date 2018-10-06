package diai.mule.components;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.StockOrder;

public class RejectOrder  implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		StockOrder order = (StockOrder) eventContext.getMessage().getPayload();
		return order;
	}

}
