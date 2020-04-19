package gr.ioannis.thesis.mapper;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import gr.ioannis.thesis.dto.ValidateUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ValidateUserMapper {

  @Mapping(target = "userAttributes", source = "validateUserDTO", qualifiedByName = "mapAttributes")
  UserDTO mapToDto(ValidateUserDTO validateUserDTO);

  @Named("mapAttributes")
  default Set<UserAttributeDTO> mapAttributes(
      ValidateUserDTO validateUserDTO) {

    UserAttributeDTO email = new UserAttributeDTO();
    email.setData(validateUserDTO.getEmail());
    email.setName("email");

    UserAttributeDTO fullName = new UserAttributeDTO();
    fullName.setData(validateUserDTO.getFullName());
    fullName.setName("fullName");

    return Stream.of(email, fullName)
        .collect(Collectors.toCollection(HashSet::new));
  }
}
