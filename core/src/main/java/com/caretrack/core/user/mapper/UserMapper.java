package com.caretrack.core.user.mapper;

import com.caretrack.core.user.domain.UserAccount;
import com.caretrack.core.user.dto.UserCreateDto;
import com.caretrack.core.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(UserAccount account);

    UserAccount toEntity(UserCreateDto dto);
}
