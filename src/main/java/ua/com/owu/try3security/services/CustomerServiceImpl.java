package ua.com.owu.try3security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.owu.try3security.dao.CustomerDAO;
import ua.com.owu.try3security.models.Customer;
import ua.com.owu.try3security.services.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerDAO.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return customerDAO.findByEmail(email);
    }

    @Override
    public UserDetails loadUserById(int id){
        return customerDAO.findById(id);
    }

    @Override
    public List<Customer> findAll(){

        return customerDAO.findAll();

    }
}
