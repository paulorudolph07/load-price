package br.com.softplan.load.price.domain.enums;

public enum TipoViaEnum {

	PAVIMENTADA("Pavimentada"), NAO_PAVIMENTADA("Não Pavimentada");
	
	private String descricao;
	
	private TipoViaEnum(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
	
}
