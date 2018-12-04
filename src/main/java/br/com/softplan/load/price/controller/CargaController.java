package br.com.softplan.load.price.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.softplan.load.price.domain.model.Carga;
import br.com.softplan.load.price.service.CargaService;

@RestController
public class CargaController {
	
	@Autowired
	private CargaService cargaService;
	
	@RequestMapping("/api/calcular-custo-transporte")
	public BigDecimal calcularCustoTransporte(@RequestParam Integer distanciaPavimentada, @RequestParam Integer distanciaNaoPavimentada,
			@RequestParam String veiculoUtilizado, @RequestParam Integer pesoTotalCarga) {
		Carga carga = cargaService.calcularCustoTransporte(distanciaPavimentada, distanciaNaoPavimentada, veiculoUtilizado, pesoTotalCarga);
		return carga.getCustoTotalCarga();
	}
	
}
