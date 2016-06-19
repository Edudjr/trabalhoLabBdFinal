package model;

public class TransportModel {
	private String company;
	private String month;
	private Double to_1000;
	private Double from_1000;
	
	public TransportModel(
			String company,
			String month,
			Double to_1000,
			Double from_1000)
	{
		this.setCompany(company);
		this.setMonth(month);
		this.setTo_1000(to_1000);
		this.setFrom_1000(from_1000);
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Double getTo_1000() {
		return to_1000;
	}
	public void setTo_1000(Double to_1000) {
		this.to_1000 = to_1000;
	}
	public Double getFrom_1000() {
		return from_1000;
	}
	public void setFrom_1000(Double from_1000) {
		this.from_1000 = from_1000;
	}
}
