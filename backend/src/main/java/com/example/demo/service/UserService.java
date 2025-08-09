package com.example.demo.service;

import com.example.demo.command.UserCommand;
import com.example.demo.exception.user.UserAlreadyCreatedException;
import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JWTUtils jwtUtils;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User getUserById(String userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
  }

  public User getUserByPseudo(String pseudo) {
    return userRepository.findByPseudo(pseudo).orElseThrow(() -> new UserNotFoundException());
  }

  public String getRole(String pseudo){
    User user = getUserByPseudo(pseudo);
    return user.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("USER");
  }

  public User verifyUser(UserCommand userCommand) {
    User user = userRepository.findByPseudo(userCommand.pseudo()).orElseThrow(() -> new UserNotFoundException());

    if (!passwordEncoder.matches(userCommand.password(), user.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }

    return user;
  }

  public String generateToken(UserCommand userCommand) {
    User user = verifyUser(userCommand);
    String jwt = jwtUtils.generateToken(user);
    return jwt;
  }

  public User createUser(String pseudo, String password) {
    Optional<User> user = userRepository.findByPseudo(pseudo);
    if (!user.isPresent()) {
      User newUser = new User(pseudo, passwordEncoder.encode(password));
      userRepository.save(newUser);
      return newUser;
    }
    
    throw new UserAlreadyCreatedException();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByPseudo(username).orElseThrow(() -> new UserNotFoundException());
  }
}
