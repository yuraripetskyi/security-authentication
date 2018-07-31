package ua.com.owu.try3security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.owu.try3security.models.Customer;

import java.util.List;

public interface CustomerService extends UserDetailsService {
    void save (Customer customer);


    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    UserDetails loadUserById(int id);

    List<Customer> findAll();
}
