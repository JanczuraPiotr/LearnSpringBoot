package pl.janczura.LearnSpringBoot.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janczura.LearnSpringBoot.customer.model.Customer;
import pl.janczura.LearnSpringBoot.customer.model.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
