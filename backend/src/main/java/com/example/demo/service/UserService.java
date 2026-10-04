package com.example.demo.service;

import com.example.demo.command.UserCommand;
import com.example.demo.exception.user.UserAlreadyCreatedException;
import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final JWTUtils jwtUtils;

  private final PasswordEncoder passwordEncoder;

  public UserService(
      UserRepository userRepository,
      JWTUtils jwtUtils,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtUtils = jwtUtils;
    this.passwordEncoder = passwordEncoder;
  }

  /* ------------------- GET --------------------------- */

  public User getUserById(String userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
  }

  public User getUserByPseudo(String pseudo) {
    return userRepository.findByPseudo(pseudo).orElseThrow(() -> new UserNotFoundException());
  }

  public String getRole(String pseudo) {
    User user = getUserByPseudo(pseudo);
    return user.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("USER");
  }

  public String generateToken(UserCommand userCommand) {
    User user = verifyUser(userCommand);
    log.info("Authentication for userId={}", user.getId());
    String jwt = jwtUtils.generateToken(user);
    return jwt;
  }

  /**
   * Create new user with pseudo and password
   * If pseudo already exists, throw new UserAlreadyCreatedException
   * 
   * @param pseudo   String : pseudo of new user
   * @param password String : password of new user
   * @return User :
   */
  public User createUser(String pseudo, String password) {
    Optional<User> user = userRepository.findByPseudo(pseudo);
    if (!user.isPresent()) {
      User newUser = new User(pseudo, passwordEncoder.encode(password));
      userRepository.save(newUser);
      log.info("User with pseudo = {} and id = {} have been created", pseudo, newUser.getId());
      return newUser;
    }

    log.info("Creation of user have been failed because pseudo = {} is already used", pseudo);

    throw new UserAlreadyCreatedException();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByPseudo(username).orElseThrow(() -> new UserNotFoundException());
  }

  /* --------------------- PRIVATE ------------------ */

  private User verifyUser(UserCommand userCommand) {
    User user = userRepository.findByPseudo(userCommand.pseudo()).orElseThrow(() -> new UserNotFoundException());

    if (!passwordEncoder.matches(userCommand.password(), user.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }

    return user;
  }
}
