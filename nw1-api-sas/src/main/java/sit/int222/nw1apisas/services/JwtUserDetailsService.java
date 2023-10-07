package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Create a GrantedAuthority using the user's role
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        authorities.add(authority);


        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                authorities);
    }
}
