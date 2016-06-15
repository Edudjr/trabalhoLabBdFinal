package model;

public class CustomerShoppingModel {
	private String client;
	private String clientId;
    private Double total;
    private Integer purchasesNumber;
    private Integer year;

    /**
     * Default constructor.
     */
    public CustomerShoppingModel() {
    }

    public CustomerShoppingModel(String client,
    		String clientId,
    		Double total, 
    		Integer purchaseNumber,
    		Integer year) 
    {
        this.setClient(client);
        this.setClientId(clientId);
        this.setTotal(total);
        this.setPurchasesNumber(purchaseNumber);
        this.setYear(year);
    }

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getPurchasesNumber() {
		return purchasesNumber;
	}

	public void setPurchasesNumber(Integer purchasesNumber) {
		this.purchasesNumber = purchasesNumber;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
