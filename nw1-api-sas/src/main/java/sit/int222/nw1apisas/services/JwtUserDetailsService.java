package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.sql.Array;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if ("javainuse".equals(username)) {
//            return new User("javainuse",
//                    "$2a$12$Vgblxnf485UBlyROJId4jO.dZgR7wv0G.l6r8L1sj6HBvsC8NswV.",
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
        if(username != null){
            User user = userRepository.findUserByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>());
        }else{
             throw new ItemNotFoundException("User not found with username: " + username);
        }
    }
}
