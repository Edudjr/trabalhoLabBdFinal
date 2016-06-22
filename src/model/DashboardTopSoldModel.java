package model;

public class DashboardTopSoldModel {
	private String name;
	private Double quantity;
	
	public DashboardTopSoldModel(String name, Double quantity){
		this.setName(name);
		this.setQuantity(quantity);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
}
