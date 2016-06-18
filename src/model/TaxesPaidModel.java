package model;

public class TaxesPaidModel {
	private String estado;
	private String mes;
	private Double total_imposto;
	
	public TaxesPaidModel(
			String estado,
			String mes,
			Double total_imposto)
	{
		this.setEstado(estado);
		this.setMes(mes);
		this.setTotal_imposto(total_imposto);
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Double getTotal_imposto() {
		return total_imposto;
	}
	public void setTotal_imposto(Double total_imposto) {
		this.total_imposto = total_imposto;
	}
}
