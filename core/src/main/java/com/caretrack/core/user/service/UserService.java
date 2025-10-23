package com.caretrack.core.user.service;

import com.caretrack.core.user.dto.UserCreateDto;
import com.caretrack.core.user.dto.UserDto;
import com.caretrack.core.user.dto.UserUpdateDto;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDto findByUsername(String username);

    UserDetails loadUserDetails(String username);

    UserDto updateProfile(String username, UserUpdateDto updateDto);

    List<UserDto> findAll();

    UserDto create(UserCreateDto createDto);

    UserDto updateRoles(Long id, Set<String> roles);
}
