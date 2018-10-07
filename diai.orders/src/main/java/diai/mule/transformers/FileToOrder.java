package diai.mule.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

import diai.mule.entities.Client;
import diai.mule.entities.Order;

public class FileToOrder extends AbstractTransformer {

	public FileToOrder() {
		this.registerSourceType(DataTypeFactory.STRING);
		this.setReturnDataType(DataTypeFactory.create(Collection.class));
	}

	@Override
	protected Object doTransform(Object src, String encoding) {
		System.out.println("Entrando en el transformador con "+src);
		ArrayList<Order> resultado = new ArrayList<>();
		String [] datos = ((String) src).split("\n");
		ArrayList<String> temp = new ArrayList<>();
		
		for ( int i = 0 ; i < datos.length ; i ++)
		{
			String[] detalle = datos[i].split(";");
			if(!detalle[0].contains("DNI") && !detalle[0].contains("ISBN"))
			{
				if(detalle.length > 2) {
					temp.add(detalle[0]);
					temp.add(detalle[1]);
					temp.add(detalle[2]);
				}else {
					temp.add(detalle[0]);
					temp.add(detalle[1]);
				}
			}
			if(detalle[0].contains("DNI") && i > 0 || i == datos.length -1) {
				resultado.add(createOrder(temp));
				temp = new ArrayList<>();
			}
		}

		return resultado;
	}

	private Order createOrder(ArrayList<String> detalle) {

		Order orden = new Order();
		Client client = new Client();
		client.setDni(detalle.get(0));
		client.setNombre(detalle.get(1));
		orden.setClient(client);
		orden.setFinanciacion(detalle.get(2).contains("S"));
		for(int i = 3 ; i < detalle.size() ; i+=2) {
			orden.addProducto(detalle.get(i), Integer.parseInt(detalle.get(i+1).trim().toString()));
		}

		return orden;
	}

}
