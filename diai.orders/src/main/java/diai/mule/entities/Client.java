package diai.mule.entities;

public class Client {
	private String nombre;
	private String dni;
	private Double debt;
	private Double lastMonth;

	public Client() {
	}

	public Double getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(Double lastMonth) {
		this.lastMonth = lastMonth;
	}

	public Double getDebt() {
		return debt;
	}

	public void setDebt(Double debt) {
		this.debt = debt;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
}