package diai.mule.transformers;

import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import diai.mule.entities.Order;

public class HttpQueryToOrden implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		Order orden = new Order();
		try {
			Map<String, String> queryParams = eventContext.getMessage().getInboundProperty("http.query.params");
			int corte = queryParams.size();
			if (corte > 0) {

				System.out.println(queryParams);

				orden.getClient().setNombre(queryParams.get("nombre"));
				orden.getClient().setDni(queryParams.get("nif"));
				String financiacion = queryParams.get("financia");
				orden.setFinanciacion(financiacion.equals("on"));

				queryParams.forEach((key, value) -> {
					if (!key.equals("nombre") && !key.equals("nif") && !key.equals("financia")
							&& !key.equals("direccion")) {
						System.out.println(key + ": " + value);
						orden.addProducto(key, Integer.parseInt(queryParams.get(key)));
					}
				});
			}
		} catch (Exception ex) {
			System.out.println("Error trying to process data");
			ex.printStackTrace();
		}
		return orden;
	}

}
