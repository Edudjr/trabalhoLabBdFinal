package model;

public class SalesYearStateModel {
	private String country;
	private String state;
	private Double total_state;
	private Double p_country;
	private Double p_total;
	
	public SalesYearStateModel(
				String country,
				String state,
				Double total_state,
				Double p_country,
				Double p_total)
	{
		this.setCountry(country);
		this.setState(state);
		this.setTotal_state(total_state);
		this.setP_country(p_country);
		this.setP_total(p_total);
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getTotal_state() {
		return total_state;
	}
	public void setTotal_state(Double total_state) {
		this.total_state = total_state;
	}
	public Double getP_country() {
		return p_country;
	}
	public void setP_country(Double p_country) {
		this.p_country = p_country;
	}
	public Double getP_total() {
		return p_total;
	}
	public void setP_total(Double p_total) {
		this.p_total = p_total;
	}
}
