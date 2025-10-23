package com.caretrack.persistence.service;

import com.caretrack.core.user.domain.UserAccount;
import com.caretrack.core.user.dto.UserCreateDto;
import com.caretrack.core.user.dto.UserDto;
import com.caretrack.core.user.dto.UserUpdateDto;
import com.caretrack.core.user.mapper.UserMapper;
import com.caretrack.core.user.service.UserService;
import com.caretrack.persistence.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userAccountRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findByUsername(String username) {
        return userAccountRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }

    @Override
    public UserDetails loadUserDetails(String username) {
        UserAccount account = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
        return new User(account.getUsername(), account.getPassword(), account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList());
    }

    @Override
    @Transactional
    public UserDto updateProfile(String username, UserUpdateDto updateDto) {
        UserAccount account = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
        account.setFullName(updateDto.getFullName());
        return userMapper.toDto(userAccountRepository.save(account));
    }

    @Override
    public List<UserDto> findAll() {
        return userAccountRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    @Transactional
    public UserDto create(UserCreateDto createDto) {
        UserAccount account = userMapper.toEntity(createDto);
        account.setPassword(passwordEncoder.encode(createDto.getPassword()));
        if (createDto.getRoles() != null && !createDto.getRoles().isEmpty()) {
            account.setRoles(createDto.getRoles().stream().map(String::toUpperCase).collect(java.util.stream.Collectors.toSet()));
        } else {
            account.setRoles(Set.of("USER"));
        }
        UserAccount saved = userAccountRepository.save(account);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional
    public UserDto updateRoles(Long id, Set<String> roles) {
        UserAccount account = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        account.setRoles(roles.stream().map(String::toUpperCase).collect(java.util.stream.Collectors.toSet()));
        UserAccount saved = userAccountRepository.save(account);
        return userMapper.toDto(saved);
    }
}
