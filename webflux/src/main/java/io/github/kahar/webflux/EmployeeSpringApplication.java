package io.github.kahar.webflux;


import io.github.kahar.webflux.employee.EmployeeWebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;

@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class EmployeeSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeSpringApplication.class, args);

        EmployeeWebClient employeeWebClient = new EmployeeWebClient();
        employeeWebClient.consume();
    }

}