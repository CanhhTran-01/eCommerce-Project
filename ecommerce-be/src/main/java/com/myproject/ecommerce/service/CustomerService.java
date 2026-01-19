package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.CustomerRequest;
import com.myproject.ecommerce.dto.response.CustomerResponse;
import com.myproject.ecommerce.entity.CustomerEntity;
import com.myproject.ecommerce.mapper.CustomerMapper;
import com.myproject.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    // thêm mới customer
    public CustomerResponse createCustomer(CustomerRequest customerRequest){
        CustomerEntity customerEntity = customerMapper.toEntity(customerRequest);
        return customerMapper.toResponse(customerRepository.save(customerEntity));

    }

    // lấy ra danh sách customers
    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomerList(){
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    // update customer
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest){
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerMapper.updateCustomer(customerEntity, customerRequest);
        return customerMapper.toResponse(customerRepository.save(customerEntity));
    }

    // delete customer
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
