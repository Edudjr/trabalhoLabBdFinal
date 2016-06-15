package model;

public class CustomerCountryStateModel {
	private String country;
	private String state;
    private Integer total;
    
    public CustomerCountryStateModel(
    		String country, 
    		String state, 
    		Integer total)
    {
    	this.setCountry(country);
    	this.setState(state);
    	this.setTotal(total);
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
    
}
