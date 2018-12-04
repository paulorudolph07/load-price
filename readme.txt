LoadPrice

IDE utiilizada: Spring Tool Suite 4, Version: 4.0.1.RELEASE

Framework: Spring Boot 2.1.1.RELEASE

Banco de Dados: MySql 5.7
nome do database: loadpricedb
url: jdbc:mysql://localhost:3306/loadpricedb
usuario: loadprice
password: loadprice123

Deploy:
	O deploy da aplicaçã pode ser feito através da linha de comando:
	  	./mvnw spring-boot:run
	  	or
	  	./mvnw clean package
	Em seguida executar:
		java -jar target/load-price-1.0.0.jar 