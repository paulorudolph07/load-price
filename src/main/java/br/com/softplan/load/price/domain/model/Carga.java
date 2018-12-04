package br.com.softplan.load.price.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Carga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@ManyToOne
	private Veiculo veiculoUtilizado;
	@ManyToOne(cascade = CascadeType.ALL)
	private Percurso percursoPavimentado;
	@ManyToOne(cascade = CascadeType.ALL)
	private Percurso percursoNaoPavimentado;
	private Integer pesoTotalTonelada;
	private BigDecimal custoTotalCarga;
	private LocalDate dataSaida;
	private LocalDate dataChega;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Veiculo getVeiculoUtilizado() {
		return veiculoUtilizado;
	}
	public void setVeiculoUtilizado(Veiculo veiculoUtilizado) {
		this.veiculoUtilizado = veiculoUtilizado;
	}
	public Percurso getPercursoPavimentado() {
		return percursoPavimentado;
	}
	public void setPercursoPavimentado(Percurso percursoPavimentado) {
		this.percursoPavimentado = percursoPavimentado;
	}
	public Percurso getPercursoNaoPavimentado() {
		return percursoNaoPavimentado;
	}
	public void setPercursoNaoPavimentado(Percurso percursoNaoPavimentado) {
		this.percursoNaoPavimentado = percursoNaoPavimentado;
	}
	public Integer getPesoTotalTonelada() {
		return pesoTotalTonelada;
	}
	public void setPesoTotalTonelada(Integer pesoTotalTonelada) {
		this.pesoTotalTonelada = pesoTotalTonelada;
	}
	public BigDecimal getCustoTotalCarga() {
		return custoTotalCarga;
	}
	public void setCustoTotalCarga(BigDecimal custoTotalCarga) {
		this.custoTotalCarga = custoTotalCarga;
	}
	public LocalDate getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}
	public LocalDate getDataChega() {
		return dataChega;
	}
	public void setDataChega(LocalDate dataChega) {
		this.dataChega = dataChega;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carga other = (Carga) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
