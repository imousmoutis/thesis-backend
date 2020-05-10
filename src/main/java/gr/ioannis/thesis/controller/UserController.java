package gr.ioannis.thesis.controller;

import com.eurodyn.qlack.fuse.aaa.annotation.ResourceAccess;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria.UserAttributeCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria.UserSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/count")
  @ResourceAccess(roleAccess = {"Administrator"})
  public long getUsersCount() {
    return userService.findUserCount(UserSearchCriteriaBuilder.createCriteria().build());
  }

  @GetMapping
  @ResourceAccess(roleAccess = {"Administrator"})
  public Iterable<UserDTO> getUsers(@RequestParam int page,
      @RequestParam int size, @RequestParam String sortColumn, @RequestParam String sortOrder,
      @RequestParam String username) {

    UserSearchCriteria userSearchCriteria = UserSearchCriteriaBuilder.createCriteria()
        .withUsernameLike("%" + username + "%").build();
    userSearchCriteria.setPageable(PageRequest.of(page, size, new Sort(sortOrder.equalsIgnoreCase("asc") ?
        Direction.ASC : Direction.DESC,
        sortColumn)));

    return userService.findUsers(userSearchCriteria);
  }

  @PostMapping()
  @ResourceAccess(roleAccess = {"Administrator"})
  public Response updateUser(@RequestBody UserDTO userDTO) {
    userService.updateUser(userDTO, false, false);
    return Response.ok(userDTO.getId()).build();
  }

  @DeleteMapping(value = "/{userId}")
  @ResourceAccess(roleAccess = {"Administrator"})
  public Response deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    return Response.ok(userId).build();
  }

  @GetMapping(value = "/unique")
  public boolean uniqueUser(@RequestParam String term) {
    UserSearchCriteria userSearchCriteria = UserSearchCriteriaBuilder
        .createCriteria().withUsername(term).build();
    Iterable<UserDTO> userDTOS = userService.findUsers(userSearchCriteria);

    return ((Collection<?>) userDTOS).isEmpty();
  }

}
