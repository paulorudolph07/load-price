LoadPrice

IDE utiilizada: Spring Tool Suite 4, Version: 4.0.1.RELEASE

Framework: Spring Boot 2.1.1.RELEASE

Banco de Dados: MySql 5.7
nome do database: loadpricedb
url: jdbc:mysql://localhost:3306/loadpricedb
usuario: loadprice
password: loadprice123

Deploy:
	O deploy da aplicação pode ser feito através da linha de comando:
	  	./mvnw spring-boot:run
	  	ou
	  	./mvnw clean package

Executar através da linha de comando:
		java -jar target/load-price-1.0.0.jar 
		
Executar usando docker:
	Caso a execução seja pelo docker, deve ser alterada a url do banco de dados, no arquivo application.yml, para jdbc:mysql://mysql-standalone:3306/loadpricedb

	docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=loadpricedb -e MYSQL_USER=loadprice -e MYSQL_PASSWORD=loadprice123 -d mysql:5.7
	
	# acessar o diretório do projeto
	docker build . -t load-price
	docker run -p 8080:8080 --name load-price --link mysql-standalone:mysql -d load-price
	
	ou
	
	Executar o arquivo run.sh localizado no diretório raíz do projeto:
		chmod +x run.sh
		./run.sh
