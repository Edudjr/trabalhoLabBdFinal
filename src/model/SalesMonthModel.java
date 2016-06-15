package model;

public class SalesMonthModel {
	private String date;
	private Double media;
	private Integer total;
    
    public SalesMonthModel(
    		String date,
    		Integer total,
    		Double media)
    {
    	this.setDate(date);
    	this.setTotal(total);
    	this.setMedia(media);
    }

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Double getMedia() {
		return media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
