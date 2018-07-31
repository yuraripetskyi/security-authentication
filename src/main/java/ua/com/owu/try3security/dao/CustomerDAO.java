package ua.com.owu.try3security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.owu.try3security.models.Customer;

public interface CustomerDAO extends JpaRepository<Customer, Integer> {

    Customer findByUsername(String username);

    Customer findByEmail(String email);

    Customer findById(int id);


}
