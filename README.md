## Creating database MySQL
````sql
CREATE DATABASE `db_escola`

````
* We will add the required dependencies to Maven pom.xml file 

````java
<dependencies>
<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
	<dependency>
		<groupId>org.hibernate</groupId>
		<artifactId>hibernate-core</artifactId>
		<version>5.4.12.Final</version>
	</dependency>
	<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-entitymanager -->
	<dependency>
		<groupId>org.hibernate</groupId>
		<artifactId>hibernate-entitymanager</artifactId>
		<version>5.4.12.Final</version>
	</dependency>
	<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>8.0.19</version>
	</dependency>
</dependencies>
````
## Login example
 
![Screenshot from 2023-01-29 17-39-13](https://user-images.githubusercontent.com/93099304/215354603-4519b936-193e-4a01-ba10-64b49c169654.png)

## Teacher section 

![Screenshot from 2023-01-29 17-42-49](https://user-images.githubusercontent.com/93099304/215354743-7df806fd-6a98-4c19-83da-074a0acc31d0.png)

## Admin section

![Screenshot from 2023-01-29 17-44-34](https://user-images.githubusercontent.com/93099304/215354826-d39a4133-820b-488a-b5bd-f59c410d23d2.png)

## Student section

![Screenshot from 2023-01-29 17-54-02](https://user-images.githubusercontent.com/93099304/215355203-2faac156-1cef-4b73-babd-48a2f1516f78.png)
