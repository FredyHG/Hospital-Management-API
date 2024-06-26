package dev.fredyhg.com.hospitalquerysystem.configs;

import dev.fredyhg.com.hospitalquerysystem.models.UserModel;
import dev.fredyhg.com.hospitalquerysystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if(userModel.isEmpty()){
            throw new UsernameNotFoundException("User ["+ username +"] not found");
        }
        Hibernate.initialize(userModel.get().getRoles());

        return userModel.get();
    }
}
