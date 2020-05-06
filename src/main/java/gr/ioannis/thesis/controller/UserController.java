package gr.ioannis.thesis.controller;

import com.eurodyn.qlack.fuse.aaa.annotation.ResourceAccess;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria;
import com.eurodyn.qlack.fuse.aaa.criteria.UserSearchCriteria.UserSearchCriteriaBuilder;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/")
public class UserController {

  private UserSearchCriteria userSearchCriteria;

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    userSearchCriteria = UserSearchCriteriaBuilder.createCriteria().build();
    this.userService = userService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "count/", produces = "application/json")
  @ResponseBody
  @ResourceAccess(roleAccess = {"Administrator"})
  public long getUsersCount() {
    return userService.findUserCount(userSearchCriteria);
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ResourceAccess(roleAccess = {"Administrator"})
  public Iterable<UserDTO> getUsers(@RequestParam int page,
      @RequestParam int size, @RequestParam String sortColumn, @RequestParam String sortOrder) {
    userSearchCriteria.setPageable(PageRequest.of(page, size, new Sort(sortOrder.equalsIgnoreCase("asc") ?
        Direction.ASC : Direction.DESC,
        sortColumn)));
    return userService.findUsers(userSearchCriteria);
  }

  @RequestMapping(method = RequestMethod.POST, produces = "plain/text")
  @ResponseBody
  @ResourceAccess(roleAccess = {"Administrator"})
  public String createUser(@RequestBody UserDTO userDTO) {
    userService.updateUser(userDTO, false, false);
    return userDTO.getId();
  }

  @DeleteMapping(value = "{userId}", produces = "plain/text")
  @ResourceAccess(roleAccess = {"Administrator"})
  public String deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    return userId;
  }

}
