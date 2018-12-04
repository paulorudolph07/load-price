package br.com.softplan.load.price;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.softplan.load.price.domain.enums.TipoViaEnum;
import br.com.softplan.load.price.domain.model.Carga;
import br.com.softplan.load.price.domain.model.Percurso;
import br.com.softplan.load.price.domain.model.Rodovia;
import br.com.softplan.load.price.domain.model.Veiculo;
import br.com.softplan.load.price.repository.IRodoviaRepository;
import br.com.softplan.load.price.repository.IVeiculoRepository;
import br.com.softplan.load.price.service.CargaService;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class CustoTransporteTest {
	
	@Mock
	private CargaService cargaService;
	
	@Mock
	private IVeiculoRepository veiculoRepository;
	
	@Mock
	private IRodoviaRepository rodoviaRepository;
	
	@Test
	public void calcularCustoTransportePavimentadaTest() {
		Integer distanciaPavimentada = 100; 
		Integer distanciaNaoPavimentada = 0;
		String veiculoUtilizado = "Caminhão caçamba";
		Integer pesoTotalCarga = 8;
		
		Carga carga = new Carga();
		carga.setDataSaida(LocalDate.now());
		carga.setPesoTotalTonelada(pesoTotalCarga);
		
		// calcular o custo total da carga
		BigDecimal custoTotalCarga = BigDecimal.ZERO;
		
		Rodovia rodoviaPavimentada = new Rodovia();
		rodoviaPavimentada.setCustoKmRodado(new BigDecimal(".54"));
		when(rodoviaRepository.findByTipoVia(eq(TipoViaEnum.PAVIMENTADA))).thenReturn(rodoviaPavimentada);
		
		Rodovia rodoviaNaoPavimentada = new Rodovia();
		rodoviaNaoPavimentada.setCustoKmRodado(new BigDecimal(".62"));
		when(rodoviaRepository.findByTipoVia(eq(TipoViaEnum.NAO_PAVIMENTADA))).thenReturn(rodoviaNaoPavimentada);
		
		Percurso percursoPav = new Percurso();
		percursoPav.setDistanciaPercorrida(distanciaPavimentada);
		percursoPav.setRodoviaPercorrida(rodoviaPavimentada);
		percursoPav.setCustoTransporte(rodoviaPavimentada.getCustoKmRodado().multiply(new BigDecimal(distanciaPavimentada)));
		when(cargaService.calcularRodoviaPavimentada(eq(100))).thenReturn(percursoPav);
		
		Percurso percursoNaoPav = new Percurso();
		percursoNaoPav.setDistanciaPercorrida(distanciaNaoPavimentada);
		percursoNaoPav.setRodoviaPercorrida(rodoviaNaoPavimentada);
		percursoNaoPav.setCustoTransporte(rodoviaNaoPavimentada.getCustoKmRodado().multiply(new BigDecimal(distanciaNaoPavimentada)));
		when(cargaService.calcularRodoviaNaoPavimentada(eq(0))).thenReturn(percursoNaoPav);
		
		
		Veiculo veiculoCadastrado = new Veiculo();
		veiculoCadastrado.setMultiplicadorCusto(new BigDecimal("1.05"));
		when(veiculoRepository.findByNome(eq(veiculoUtilizado))).thenReturn(veiculoCadastrado);
		// localiza o custo do veículo pelo nome passado como parâmetro
		Veiculo veiculo = veiculoRepository.findByNome(veiculoUtilizado);
		carga.setVeiculoUtilizado(veiculo);
		
		// efetua o calculo por percurso e tipo de via
		if(distanciaPavimentada != null && distanciaPavimentada > 0) {
			Percurso percursoPavimentado = cargaService.calcularRodoviaPavimentada(distanciaPavimentada);
			// custo pela via pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoPavimentado.getCustoTransporte());
		}
		
		if(distanciaNaoPavimentada != null && distanciaNaoPavimentada > 0) {
			Percurso percursoNaoPavimentado = cargaService.calcularRodoviaNaoPavimentada(distanciaNaoPavimentada);
			// custo pela via nao pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoNaoPavimentado.getCustoTransporte());
		}
		
		// multiplica pelo fator veiculo
		custoTotalCarga = custoTotalCarga.multiply(veiculo.getMultiplicadorCusto());
		// valida o excedente da carga
		Integer excedente = carga.getPesoTotalTonelada()-CargaService.PESO_MAX_PERMITIDO;
		if(excedente > 0)
			custoTotalCarga = custoTotalCarga.add(CargaService.VALOR_CUSTO_EXCEDENTE.multiply(new BigDecimal(excedente))
					.multiply(new BigDecimal(distanciaPavimentada + distanciaNaoPavimentada)));
			
		carga.setCustoTotalCarga(custoTotalCarga);
		//when(cargaRepo.save(ArgumentMatchers.any(Carga.class))).thenReturn(carga);
		//Assert.assertEquals(new BigDecimal("105.19").toString(), carga.getCustoTotalCarga().setScale(2, RoundingMode.HALF_EVEN).toString() );
		Assert.assertEquals(62.7, carga.getCustoTotalCarga().setScale(2, RoundingMode.HALF_EVEN).doubleValue(), 0 );
	}

	@Test
	public void calcularCustoTransporteNaoPavimentadaTest() {
		Integer distanciaPavimentada = 0; 
		Integer distanciaNaoPavimentada = 180;
		String veiculoUtilizado = "Carreta";
		Integer pesoTotalCarga = 12;
		
		Carga carga = new Carga();
		carga.setDataSaida(LocalDate.now());
		carga.setPesoTotalTonelada(pesoTotalCarga);
		
		// calcular o custo total da carga
		BigDecimal custoTotalCarga = BigDecimal.ZERO;
		
		Rodovia rodoviaPavimentada = new Rodovia();
		rodoviaPavimentada.setCustoKmRodado(new BigDecimal(".54"));
		when(rodoviaRepository.findByTipoVia(eq(TipoViaEnum.PAVIMENTADA))).thenReturn(rodoviaPavimentada);
		
		Rodovia rodoviaNaoPavimentada = new Rodovia();
		rodoviaNaoPavimentada.setCustoKmRodado(new BigDecimal(".62"));
		when(rodoviaRepository.findByTipoVia(eq(TipoViaEnum.NAO_PAVIMENTADA))).thenReturn(rodoviaNaoPavimentada);
		
		Percurso percursoPav = new Percurso();
		percursoPav.setDistanciaPercorrida(distanciaPavimentada);
		percursoPav.setRodoviaPercorrida(rodoviaPavimentada);
		percursoPav.setCustoTransporte(rodoviaPavimentada.getCustoKmRodado().multiply(new BigDecimal(distanciaPavimentada)));
		when(cargaService.calcularRodoviaPavimentada(eq(0))).thenReturn(percursoPav);
		
		Percurso percursoNaoPav = new Percurso();
		percursoNaoPav.setDistanciaPercorrida(distanciaNaoPavimentada);
		percursoNaoPav.setRodoviaPercorrida(rodoviaNaoPavimentada);
		percursoNaoPav.setCustoTransporte(rodoviaNaoPavimentada.getCustoKmRodado().multiply(new BigDecimal(distanciaNaoPavimentada)));
		when(cargaService.calcularRodoviaNaoPavimentada(eq(180))).thenReturn(percursoNaoPav);
		
		
		Veiculo veiculoCadastrado = new Veiculo();
		veiculoCadastrado.setMultiplicadorCusto(new BigDecimal("1.12"));
		when(veiculoRepository.findByNome(eq(veiculoUtilizado))).thenReturn(veiculoCadastrado);
		// localiza o custo do veículo pelo nome passado como parâmetro
		Veiculo veiculo = veiculoRepository.findByNome(veiculoUtilizado);
		carga.setVeiculoUtilizado(veiculo);
		
		// efetua o calculo por percurso e tipo de via
		if(distanciaPavimentada != null && distanciaPavimentada > 0) {
			Percurso percursoPavimentado = cargaService.calcularRodoviaPavimentada(distanciaPavimentada);
			// custo pela via pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoPavimentado.getCustoTransporte());
		}
		
		if(distanciaNaoPavimentada != null && distanciaNaoPavimentada > 0) {
			Percurso percursoNaoPavimentado = cargaService.calcularRodoviaNaoPavimentada(distanciaNaoPavimentada);
			// custo pela via nao pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoNaoPavimentado.getCustoTransporte());
		}
		
		// multiplica pelo fator veiculo
		custoTotalCarga = custoTotalCarga.multiply(veiculo.getMultiplicadorCusto());
		// valida o excedente da carga
		Integer excedente = carga.getPesoTotalTonelada()-CargaService.PESO_MAX_PERMITIDO;
		if(excedente > 0)
			custoTotalCarga = custoTotalCarga.add(CargaService.VALOR_CUSTO_EXCEDENTE.multiply(new BigDecimal(excedente))
					.multiply(new BigDecimal(distanciaPavimentada + distanciaNaoPavimentada)));
			
		carga.setCustoTotalCarga(custoTotalCarga);
		//when(cargaRepo.save(ArgumentMatchers.any(Carga.class))).thenReturn(carga);
		//Assert.assertEquals(new BigDecimal("105.19").toString(), carga.getCustoTotalCarga().setScale(2, RoundingMode.HALF_EVEN).toString() );
		Assert.assertEquals(150.19, carga.getCustoTotalCarga().setScale(2, RoundingMode.HALF_EVEN).doubleValue(), 0 );
	}
	
	@Test
	public void calcularCustoTransporteDuasRodoviasTest() {
		Integer distanciaPavimentada = 80; 
		Integer distanciaNaoPavimentada = 20;
		String veiculoUtilizado = "Caminhão baú";
		Integer pesoTotalCarga = 6;
		
		Carga carga = new Carga();
		carga.setDataSaida(LocalDate.now());
		carga.setPesoTotalTonelada(pesoTotalCarga);
		
		// calcular o custo total da carga
		BigDecimal custoTotalCarga = BigDecimal.ZERO;
		
		Rodovia rodoviaPavimentada = new Rodovia();
		rodoviaPavimentada.setCustoKmRodado(new BigDecimal(".54"));
		when(rodoviaRepository.findByTipoVia(eq(TipoViaEnum.PAVIMENTADA))).thenReturn(rodoviaPavimentada);
		
		Rodovia rodoviaNaoPavimentada = new Rodovia();
		rodoviaNaoPavimentada.setCustoKmRodado(new BigDecimal(".62"));
		when(rodoviaRepository.findByTipoVia(eq(TipoViaEnum.NAO_PAVIMENTADA))).thenReturn(rodoviaNaoPavimentada);
		
		Percurso percursoPav = new Percurso();
		percursoPav.setDistanciaPercorrida(distanciaPavimentada);
		percursoPav.setRodoviaPercorrida(rodoviaPavimentada);
		percursoPav.setCustoTransporte(rodoviaPavimentada.getCustoKmRodado().multiply(new BigDecimal(distanciaPavimentada)));
		when(cargaService.calcularRodoviaPavimentada(eq(80))).thenReturn(percursoPav);
		
		Percurso percursoNaoPav = new Percurso();
		percursoNaoPav.setDistanciaPercorrida(distanciaNaoPavimentada);
		percursoNaoPav.setRodoviaPercorrida(rodoviaNaoPavimentada);
		percursoNaoPav.setCustoTransporte(rodoviaNaoPavimentada.getCustoKmRodado().multiply(new BigDecimal(distanciaNaoPavimentada)));
		when(cargaService.calcularRodoviaNaoPavimentada(eq(20))).thenReturn(percursoNaoPav);
		
		
		Veiculo veiculoCadastrado = new Veiculo();
		veiculoCadastrado.setMultiplicadorCusto(new BigDecimal("1.00"));
		when(veiculoRepository.findByNome(eq(veiculoUtilizado))).thenReturn(veiculoCadastrado);
		// localiza o custo do veículo pelo nome passado como parâmetro
		Veiculo veiculo = veiculoRepository.findByNome(veiculoUtilizado);
		carga.setVeiculoUtilizado(veiculo);
		
		// efetua o calculo por percurso e tipo de via
		if(distanciaPavimentada != null && distanciaPavimentada > 0) {
			Percurso percursoPavimentado = cargaService.calcularRodoviaPavimentada(distanciaPavimentada);
			// custo pela via pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoPavimentado.getCustoTransporte());
		}
		
		if(distanciaNaoPavimentada != null && distanciaNaoPavimentada > 0) {
			Percurso percursoNaoPavimentado = cargaService.calcularRodoviaNaoPavimentada(distanciaNaoPavimentada);
			// custo pela via nao pavimentada
			custoTotalCarga = custoTotalCarga.add(percursoNaoPavimentado.getCustoTransporte());
		}
		
		// multiplica pelo fator veiculo
		custoTotalCarga = custoTotalCarga.multiply(veiculo.getMultiplicadorCusto());
		// valida o excedente da carga
		Integer excedente = carga.getPesoTotalTonelada()-CargaService.PESO_MAX_PERMITIDO;
		if(excedente > 0)
			custoTotalCarga = custoTotalCarga.add(CargaService.VALOR_CUSTO_EXCEDENTE.multiply(new BigDecimal(excedente))
					.multiply(new BigDecimal(distanciaPavimentada + distanciaNaoPavimentada)));
			
		carga.setCustoTotalCarga(custoTotalCarga);
		//Assert.assertEquals(new BigDecimal("105.19").toString(), carga.getCustoTotalCarga().setScale(2, RoundingMode.HALF_EVEN).toString() );
		Assert.assertEquals(57.60, carga.getCustoTotalCarga().setScale(2, RoundingMode.HALF_EVEN).doubleValue(), 0 );
	}
}
