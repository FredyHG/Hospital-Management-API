package dev.dracarys.com.hospitalquerysystem.configs;

import dev.dracarys.com.hospitalquerysystem.dominio.UserModel;
import dev.dracarys.com.hospitalquerysystem.repository.UserRepository;
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

        return new DetailsUserData(userModel);
    }
}
