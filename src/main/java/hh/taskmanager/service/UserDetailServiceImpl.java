package hh.taskmanager.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.AppUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    // Constructor injection of AppUserRepository
    private final AppUserRepository appUserRepository;

    public UserDetailServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // Spring Secutiry calls this method to load user details during authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser currUser = appUserRepository.findByUsername(username);
        if (currUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

    // Found user will be wrapped in a Spring Security 'User' object (username + password + role)
        return new org.springframework.security.core.userdetails.User(
                currUser.getUsername(),
                currUser.getPassword(),
                AuthorityUtils.createAuthorityList(currUser.getRole()));
    }
}