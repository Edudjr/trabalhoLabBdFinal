package model;

public class SalesMonthModel {
	private String data;
	private Double total;
    
    public SalesMonthModel(
    		String data, 
    		Double total)
    {
    	this.setData(data);
    	this.setTotal(total);
    }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
