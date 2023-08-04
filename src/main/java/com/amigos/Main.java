package com.amigos;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public GreetResponse greet() {
        return new GreetResponse("Hey");
    }

    @RequestMapping("/msg")
    public FavProgrammings msg() {
        return new FavProgrammings(
                "Hello", List.of("SC", "DC"), new Person("Qasim", 22, 9_700.25, true)
        );
    }

    record GreetResponse(String greet) {}

    record Person(String name, int age, double cash, boolean present) {}

    record FavProgrammings(
            String lang,
            List<String> favList,
            Person person
    ) {}

    // Customers DB

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("api/v1/customers")
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }


}
