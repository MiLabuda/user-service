package com.wszib.userservice.adapter.in.web.rest;

import com.wszib.userservice.adapter.in.web.rest.mapping.UserMappingFactory;
import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.command.CreateUserCommand;
import com.wszib.userservice.domain.error.AccessDeniedException;
import com.wszib.userservice.domain.error.NullException;
import com.wszib.userservice.domain.querry.FilterCriteria;
import com.wszib.userservice.application.ports.in.ChangeUserDetailsUseCase;
import com.wszib.userservice.application.ports.in.CreateUserUseCase;
import com.wszib.userservice.application.ports.in.GetUserUseCase;
import com.wszib.userservice.application.ports.in.RemoveUserUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.infrastructure.adapter.DriverAdapter;
import com.wszib.userservice.infrastructure.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/users")
@DriverAdapter
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    Function<User, UserDTO> userDomainToDTOMapper;
    Function<CreateUserDTO, CreateUserCommand> createUserDTOToCommandMapper;

    private final GetUserUseCase getUserUseCase;
    private final RemoveUserUseCase removeUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final ChangeUserDetailsUseCase changeUserDetailsUseCase;

    @Autowired
    UserController(GetUserUseCase getUserUseCase,
                   RemoveUserUseCase removeUserUseCase,
                   CreateUserUseCase createUserUseCase,
                   ChangeUserDetailsUseCase changeUserDetailsUseCase) {
        this(getUserUseCase,
                removeUserUseCase,
                createUserUseCase,
                changeUserDetailsUseCase,
                UserMappingFactory.createUserDomainToDTOMapper(),
                UserMappingFactory.createUserDTOToCommandMapper());
    }

    UserController(GetUserUseCase getUserUseCase,
                   RemoveUserUseCase removeUserUseCase,
                   CreateUserUseCase createUserUseCase,
                   ChangeUserDetailsUseCase changeUserDetailsUseCase,
                   Function<User, UserDTO> userDomainToDTOMapper,
                   Function<CreateUserDTO, CreateUserCommand> createUserDTOToCommandMapper) {
        this.getUserUseCase = getUserUseCase;
        this.removeUserUseCase = removeUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.changeUserDetailsUseCase = changeUserDetailsUseCase;
        this.userDomainToDTOMapper = userDomainToDTOMapper;
        this.createUserDTOToCommandMapper = createUserDTOToCommandMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> getAll(
            @RequestParam(name = "email", required = false) Optional<String> email,
            @RequestParam(name = "firstName", required = false) Optional<String> firstName,
            @RequestParam(name = "lastName", required = false) Optional<String> lastName,
            @RequestParam(name = "active", required = false) Optional<Boolean> active) {
        LOGGER.info("Incoming request to get all users based on filter criteria");

        final FilterCriteria filterCriteria = new FilterCriteria(email, firstName, lastName, active);
        return getUserUseCase.findAllBy(filterCriteria)
                .stream()
                .map(userDomainToDTOMapper)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        LOGGER.info("Incoming request to get user by id");
        validateUserAccess(id);
        return userDomainToDTOMapper.apply(getUserUseCase.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO) {
        LOGGER.info("Incoming request to create user");

        CreateUserCommand command = createUserDTOToCommandMapper.apply(createUserDTO);
        createUserUseCase.handle(command);
        URI location = URI.create("/users/" + command.userId().value());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        LOGGER.info("Incoming request to delete user by id");
        validateUserAccess(id);
        removeUserUseCase.remove(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateUser(
            @PathVariable String id,
            @RequestBody ChangeUserDetailsDTO changeUserDetailsDTO) {
        LOGGER.info("Incoming request to update user by id");
        if(id == null) throw NullException.forId();
        validateUserAccess(id);

        ChangeUserDetailsCommand command = UserMappingFactory.createChangeUserDetailsDTOToCommandMapper(id).apply(changeUserDetailsDTO);

        changeUserDetailsUseCase.handle(command);
        URI location = URI.create("/users/" + command.userId().value());
        return ResponseEntity.created(location).build();
    }

    private static void validateUserAccess(String id) {
        if (!UserUtils.isAdmin() && !UserUtils.isSelf(id)) {
            LOGGER.warn("Access denied for user: {}", id);
            throw AccessDeniedException.forUser(id);
        }
    }
}
