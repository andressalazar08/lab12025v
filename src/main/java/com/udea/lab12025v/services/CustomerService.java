package com.udea.lab12025v.services;


import com.udea.lab12025v.DTO.CustomerDTO;
import com.udea.lab12025v.entity.Customer;
import com.udea.lab12025v.mapper.CustomerMapper;
import com.udea.lab12025v.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO)//Se usa customerMapper en luger de CustomerMapper.INSTANCE
                .toList();
    }

    public CustomerDTO getCustomerById(Long id){
        return customerRepository.findById(id).map(customerMapper::toDTO)
                .orElseThrow(()->new RuntimeException("Customer not found"));
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO){
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDTO(customerRepository.save(customer));
    }


}