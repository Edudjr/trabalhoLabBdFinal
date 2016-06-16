package model;

public class ProductSalesCategoryModel {
	private String categoria;
	private String subcategoria;
	private String produto;
	private String data_venda;
	private Integer unidade;
	private Float total_vendido;
	private Integer rank_categoria;
	private Integer rank_subcategoria;
	private Integer rank_geral;
	
	public ProductSalesCategoryModel (
			String categoria,
			String subcategoria,
			String produto,
			String data_venda,
			Integer unidade,
			Float total_vendido,
			Integer rank_categoria,
			Integer rank_subcategoria,
			Integer rank_geral)
	{
		this.setCategoria(categoria);
		this.setSubcategoria(subcategoria);
		this.setProduto(produto);
		this.setData_venda(data_venda);
		this.setUnidade(unidade);
		this.setTotal_vendido(total_vendido);
		this.setRank_categoria(rank_categoria);
		this.setRank_subcategoria(rank_subcategoria);
		this.setRank_geral(rank_geral);
	}

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getData_venda() {
		return data_venda;
	}

	public void setData_venda(String data_venda) {
		this.data_venda = data_venda;
	}

	public Integer getUnidade() {
		return unidade;
	}

	public void setUnidade(Integer unidade) {
		this.unidade = unidade;
	}

	public Float getTotal_vendido() {
		return total_vendido;
	}

	public void setTotal_vendido(Float total_vendido) {
		this.total_vendido = total_vendido;
	}

	public Integer getRank_categoria() {
		return rank_categoria;
	}

	public void setRank_categoria(Integer rank_categoria) {
		this.rank_categoria = rank_categoria;
	}

	public Integer getRank_subcategoria() {
		return rank_subcategoria;
	}

	public void setRank_subcategoria(Integer rank_subcategoria) {
		this.rank_subcategoria = rank_subcategoria;
	}

	public Integer getRank_geral() {
		return rank_geral;
	}

	public void setRank_geral(Integer rank_geral) {
		this.rank_geral = rank_geral;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
