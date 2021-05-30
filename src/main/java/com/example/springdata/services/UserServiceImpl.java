package com.example.springdata.services;

import com.example.springdata.entities.Role;
import com.example.springdata.entities.User;
import com.example.springdata.repositories.RoleRepository;
import com.example.springdata.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findOneByUsername(username);
    }

    // КАК СПРИНГ ПОЛУЧАЕТ ПОЛЬЗОВАТЕЛЕЙ?
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(userName);
        if (user==null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        //достали user'а и по нему формируем user details
        //для формирования нужно имя, пароль, список ролей
        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    // список ролей
    // преобразуем роли пользователя в роли спринга
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role-> new SimpleGrantedAuthority(role.getName()))
                .collect((Collectors.toList()));
    }


}
