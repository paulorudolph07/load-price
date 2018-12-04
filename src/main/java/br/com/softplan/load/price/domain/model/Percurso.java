package br.com.softplan.load.price.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Percurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Rodovia rodoviaPercorrida;
	private Integer distanciaPercorrida;
	private BigDecimal custoTransporte;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Rodovia getRodoviaPercorrida() {
		return rodoviaPercorrida;
	}
	public void setRodoviaPercorrida(Rodovia rodoviaPercorrida) {
		this.rodoviaPercorrida = rodoviaPercorrida;
	}
	public Integer getDistanciaPercorrida() {
		return distanciaPercorrida;
	}
	public void setDistanciaPercorrida(Integer distanciaPercorrida) {
		this.distanciaPercorrida = distanciaPercorrida;
	}
	public BigDecimal getCustoTransporte() {
		return custoTransporte;
	}
	public void setCustoTransporte(BigDecimal custoTransporte) {
		this.custoTransporte = custoTransporte;
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
		Percurso other = (Percurso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
