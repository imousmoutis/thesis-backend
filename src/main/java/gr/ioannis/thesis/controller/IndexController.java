package gr.ioannis.thesis.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDetailsDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserGroupService;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.fuse.security.service.AuthenticationService;
import com.eurodyn.qlack.fuse.security.service.LogoutService;
import gr.ioannis.thesis.dto.ValidateUserDTO;
import gr.ioannis.thesis.mapper.ValidateUserMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
public class IndexController {

  private static final String SEARCH_USER_ID = "322d4a48-cae4-4114-88ab-05da16db053e";

  private AuthenticationService authenticationService;

  private LogoutService logoutService;

  private UserService userService;

  private UserGroupService userGroupService;

  private ValidateUserMapper userMapper;

  @Autowired
  public IndexController(AuthenticationService authenticationService, LogoutService logoutService,
      UserService userService, UserGroupService userGroupService, ValidateUserMapper userMapper) {
    this.authenticationService = authenticationService;
    this.logoutService = logoutService;
    this.userService = userService;
    this.userGroupService = userGroupService;
    this.userMapper = userMapper;
  }

  @GetMapping(produces = "text/plain")
  public String homepage() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
    Date date = new Date(System.currentTimeMillis());
    return "Server is running with current timestamp " + formatter.format(date);
  }

  @PostMapping(value = "login")
  public Response login(@RequestBody UserDetailsDTO user,
      HttpServletResponse response) {
    String generatedJwt = authenticationService.authenticate(user);
    response.setHeader(HttpHeaders.AUTHORIZATION, generatedJwt);
    return Response.ok(user).build();
  }

  @DeleteMapping(value = "logout")
  public Response logout(HttpServletRequest req) {
    logoutService.performLogout(req);
    return Response.ok().build();
  }

  @PostMapping(value = "register", produces = "plain/text")
  public String createUser(@Valid @RequestBody ValidateUserDTO validateUserDTO) {
    UserDTO userDTO = userMapper.mapToDto(validateUserDTO);
    userDTO.setStatus((byte) 1);

    String userId = userService.createUser(userDTO, Optional.empty());
    userGroupService.addUser(userId, SEARCH_USER_ID);

    return userId;
  }
}
