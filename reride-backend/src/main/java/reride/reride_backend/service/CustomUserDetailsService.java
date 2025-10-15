package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.repository.EmployeeRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {



    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Employee employee = employeeRepo.findByEmployeeEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + email));

        return employeeRepo.findByEmployeeEmail(email)
                .map(emp-> buildSpringUser(emp.getEmployeeEmail(),emp.getEmployeePassword(),emp.getEmployeeRole().name()))
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    private UserDetails buildSpringUser(String email, String password, String role) {
        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(password)
                .authorities("ROLE_" + role) // Spring expects ROLE_ prefix
                .build();
    }
}
