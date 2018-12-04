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

Executar por linha de comando:
		java -jar target/load-price-1.0.0.jar 
		
Executar usando docker:
	docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=loadpricedb -e MYSQL_USER=loadprice -e MYSQL_PASSWORD=loadprice123 -d mysql:5.6
	
	docker build . -t load-price
	docker run -p 8080:8080 --name load-price --link mysql-standalone:mysql -d load-price

OBSERVAÇÃO_1:
	Caso a execução seja pelo docker deve ser alterada a url do arquivo application.yml para jdbc:mysql://mysql-standalone:3306/loadpricedb

OBSERVAÇÃO_2:
	Após a criação do banco, será necessário executar os de inserts localizado no arquivo db/migration/V1_LOADPRICE.sql
	
	*** Caso a execução seja utilizando docker, deve ser acessado o banco mysql do container
	docker exec -it mysql-standalone bash
	
	mysql -uroot -p
	Enter password: password
	
	use loadpricedb;
	
	copiar e colar os comandos inserts e executar;
	
OBSERVAÇÃO_3:
	O Flyway foi desativado, pois seria necessário configurar os scripts de criação das tabelas, sendo esse trabalho já realizado pelo JPA
