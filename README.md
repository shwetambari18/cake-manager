# cakemanager
 Waracle Cake Manager
 
 Project Technologies Used/Changed:
 1. Spring Boot
 2. Spring JPA for data transactions.
 3. Swagger for one place information about REST endpoints used in project.
	Link: http://localhost:8282/swagger-ui/index.html#/cake-controller
 4. Controllers
    For Spring MVC Model and View : CakeController
    For Spring REST : CakeRestController		
 5. Unit Testing 
    For Cake REST Layer - RestTemplate
	For MVC Layer - MockMvc
 6. In-memory database: H2
    Link: http://localhost:8282/h2-console/
 7. Front End: JSP, JQuery, Bootstrap CSS 
 8. Application Links:
	1. http://localhost:8282/ : Loads list of cakes and user can add new cake.
	After filling in all required and validating fields cake gets added to system
	and redirected to response of cake addition.
	Cake with same values can not be added again.
	2. http://localhost:8282/rest/ : Loads cakes available in system in json format.
	3. http://localhost:8282/rest/cakes/1 : Loads cake details having id 1.
	   If not it gives CakeNotFoundException.
	4. http://localhost:8282/rest/cakes/ : Downloads cakes.json file.
	9. http://localhost:8282/rest/cakes : Cake can be created at this endpoint.
	   Using Postman can test endpoint using given sample input with request type POST and body type JSON:
		{
        "name": "Sample",
        "flavour": "Cheese",
        "size": "Small",
        "icingType": "Freshcream",
        "price": 21.5,
        "id": 7
       }
	   
Running Project:
GitHub Project Link: https://github.com/shwetambari18/cake-manager   
Clone or Download project and unzip.	
From command line enter command "mvn spring-boot:run" by locating project folder.	
From browser hit link: 	http://localhost:8282/ 
 