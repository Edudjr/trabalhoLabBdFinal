package model;

public class SalesYearModel {
	private String month;
	private Double total_month;
	private Double change;
	
	public SalesYearModel(
			String month,
			Double total_month,
			Double change)
	{
		this.setMonth(month);
		this.setTotal_month(total_month);
		this.setChange(change);
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Double getTotal_month() {
		return total_month;
	}
	public void setTotal_month(Double total_month) {
		this.total_month = total_month;
	}
	public Double getChange() {
		return change;
	}
	public void setChange(Double change) {
		this.change = change;
	}
	
	
}
