package com.udea.lab12025v.controller;


import com.udea.lab12025v.DTO.CustomerDTO;
import com.udea.lab12025v.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")  //  http://localhost:8080/api/customers
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Obtener todos los clientes
    //Recursos HTTP --> URL
    //Metodos HTTP -->  GET, POST, PUT, DELETE
    //Representación del Recurso JSON / XML / Texto Plano
    //Codigos de respuesta HTTP (200 OK), (201 CREATED), 404 (NOT FOUND)

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    //Obtner un cliente por un ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getBalance() == null){
            throw new IllegalArgumentException("El Saldo no puede ser nulo");
        }
        return ResponseEntity.ok(customerService.createCustomer(customerDTO));
    }

}
