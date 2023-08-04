package com.amigos;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
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

    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }

    static public record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer Id){
        customerRepository.deleteById(Id);
    }
    @PutMapping("{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody NewCustomerRequest request) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(request.name);
            customer.setEmail(request.email);
            customer.setAge(request.age);
            customerRepository.save(customer);
            return ResponseEntity.ok("Customer updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
