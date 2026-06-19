package mapstruct;

import DTO.UserRequestDto;
import DTO.UserResponseDto;
import entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//TODO add mapper
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRequestDto userToUserRequestDto(User user);

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User userRequestDtoToUser(UserRequestDto userDto);

    @Mapping(target = "uuid", ignore = true)
    UserResponseDto userToUserResponseDto(User user);

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User userResponseDtoToUser(UserResponseDto userDto);
}
