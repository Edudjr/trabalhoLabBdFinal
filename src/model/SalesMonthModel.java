package model;

public class SalesMonthModel {
	private String date;
	private Double total;
    
    public SalesMonthModel(
    		String date, 
    		Double total)
    {
    	this.setData(date);
    	this.setTotal(total);
    }

	public String getData() {
		return date;
	}

	public void setData(String data) {
		this.date = data;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
