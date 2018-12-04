ko.extenders.required = function(target, overrideMessage) {
    //add some sub-observables to our observable
    target.hasError = ko.observable();
    target.validationMessage = ko.observable();
 
    //define a function to do validation
    function validate(newValue) {
       target.hasError(newValue ? false : true);
       target.validationMessage(newValue ? "" : overrideMessage || "This field is required");
    }
 
    //initial validation
    validate(target());
 
    //validate whenever the value changes
    target.subscribe(validate);
 
    //return the original observable
    return target;
};

function cargaViewModel() {
    var self = this;

    self.veiculos = ko.observableArray(['Caminhão baú', 'Caminhão caçamba', 'Carreta']);
    self.veiculoSelecionado = ko.observable();
    self.distanciaPavimentada = ko.observable(1).extend({ required: "Campo Obrigatório!" });
    self.distanciaNaoPavimentada = ko.observable(1).extend({ required: "Campo Obrigatório!" });
    self.pesoTotalCarga = ko.observable(1).extend({ required: "Campo Obrigatório!" });
    self.custoTransporte = ko.observable();

    self.calcularCustoTransporte = () => {
    	
    	if(!self.distanciaPavimentada() || !self.distanciaNaoPavimentada() || !self.pesoTotalCarga()) {
    		alert('Há campos obrigatórios não preenchidos!');
    		return;
    	}
			    	
    	$.post('/api/calcular-custo-transporte', 
    			{distanciaPavimentada: self.distanciaPavimentada(), 
    			distanciaNaoPavimentada: self.distanciaNaoPavimentada(),
        		veiculoUtilizado: self.veiculoSelecionado(), 
        		pesoTotalCarga: self.pesoTotalCarga()}, 
        	(returnedData) => {
    			self.custoTransporte((returnedData).toFixed(2));
    		}
        );
        /* $.ajax({
            type: 'POST',   
            url: 'http://localhost:8080/carga/calucularCustoTransporte',
            contentType: "application/javascript",
            dataType: "jsonp",
            data: {distanciaPavimentada: self.distanciaPavimentada, distanciaNaoPavimentada: self.distanciaNaoPavimentada,
            	veiculoUtilizado: self.veiculoSelecionado, pesoTotalCarga: self.pesoTotalCarga},
            success: data => {
                let observableData = ko.mapping.fromJS(data);
                self.custoTransporte(observableData());
                //var array = observableData();
                //self.customerList(array);
            },
            error: (jq, st, error) => {
                alert(error);
            }
        }); */
    };
}

$(document).ready(function () {
	console.log('app initialized...')
    ko.applyBindings(new cargaViewModel());
});