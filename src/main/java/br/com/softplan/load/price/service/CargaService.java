package br.com.softplan.load.price.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.softplan.load.price.domain.enums.TipoViaEnum;
import br.com.softplan.load.price.domain.model.Carga;
import br.com.softplan.load.price.domain.model.Percurso;
import br.com.softplan.load.price.domain.model.Rodovia;
import br.com.softplan.load.price.domain.model.Veiculo;
import br.com.softplan.load.price.repository.ICargaRepository;
import br.com.softplan.load.price.repository.IRodoviaRepository;
import br.com.softplan.load.price.repository.IVeiculoRepository;

@Service
public class CargaService {

	@Autowired
	private IVeiculoRepository veiculoRepository;
	
	@Autowired
	private IRodoviaRepository rodoviaRepository;
	
	@Autowired
	private ICargaRepository cargaRepository;
	
	public static final Integer PESO_MAX_PERMITIDO = 5;
	public static final BigDecimal VALOR_CUSTO_EXCEDENTE = new BigDecimal(".02");
	
	public Percurso calcularRodoviaPavimentada(Integer distancia) {
		Rodovia pavimentada = rodoviaRepository.findByTipoVia(TipoViaEnum.PAVIMENTADA);
		
		Percurso percurso = new Percurso();
		percurso.setDistanciaPercorrida(distancia);
		percurso.setRodoviaPercorrida(pavimentada);
		percurso.setCustoTransporte(pavimentada.getCustoKmRodado().multiply(new BigDecimal(distancia)));
		
		return percurso;
	}
	
	public Percurso calcularRodoviaNaoPavimentada(Integer distancia) {
		Rodovia naoPavimentada = rodoviaRepository.findByTipoVia(TipoViaEnum.NAO_PAVIMENTADA);
		
		Percurso percurso = new Percurso();
		percurso.setDistanciaPercorrida(distancia);
		percurso.setRodoviaPercorrida(naoPavimentada);
		percurso.setCustoTransporte(naoPavimentada.getCustoKmRodado().multiply(new BigDecimal(distancia)));
		
		return percurso;
	}
	
	public Carga calcularCustoTransporte(Integer distanciaPavimentada, Integer distanciaNaoPavimentada,
			 String veiculoUtilizado, Integer pesoTotalCarga) {
		Carga carga = new Carga();
		carga.setDataSaida(LocalDate.now());
		carga.setPesoTotalTonelada(pesoTotalCarga);

		// calcular o custo total da carga
		BigDecimal custoTotalCarga = BigDecimal.ZERO;
		
		// localiza o custo do veículo pelo nome passado como parâmetro
		Veiculo veiculo = veiculoRepository.findByNome(veiculoUtilizado);
		carga.setVeiculoUtilizado(veiculo);
		
		// efetua o calculo por percurso e tipo de via
		if(distanciaPavimentada != null && distanciaPavimentada > 0) {
			Percurso percursoPavimentado = calcularRodoviaPavimentada(distanciaPavimentada);
			// custo pela via pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoPavimentado.getCustoTransporte());
		}
		
		if(distanciaNaoPavimentada != null && distanciaNaoPavimentada > 0) {
			Percurso percursoNaoPavimentado = calcularRodoviaNaoPavimentada(distanciaNaoPavimentada);
			// custo pela via nao pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoNaoPavimentado.getCustoTransporte());
		}
		
		// multiplica pelo fator veiculo
		custoTotalCarga = custoTotalCarga.multiply(veiculo.getMultiplicadorCusto());
		// valida o excedente da carga
		Integer excedente = carga.getPesoTotalTonelada()-PESO_MAX_PERMITIDO;
		if(excedente > 0)
			custoTotalCarga = custoTotalCarga.add(VALOR_CUSTO_EXCEDENTE.multiply(new BigDecimal(excedente))
					.multiply(new BigDecimal(distanciaPavimentada + distanciaNaoPavimentada)));
			
		carga.setCustoTotalCarga(custoTotalCarga);
		
		return cargaRepository.save(carga);
	}
	
}
