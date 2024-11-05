package com.wszib.userservice.adapter.in.web.rest;

import com.wszib.userservice.adapter.in.web.rest.mapping.UserMappingFactory;
import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.adapter.in.web.rest.model.InvalidStatusChangeException;
import com.wszib.userservice.adapter.in.web.rest.model.RegisterUserDTO;
import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.application.ports.in.*;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.command.RegisterUserCommand;
import com.wszib.userservice.domain.error.AccessDeniedException;
import com.wszib.userservice.domain.error.NullException;
import com.wszib.userservice.domain.querry.FilterCriteria;
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
import java.util.function.Function;

@RestController
@RequestMapping("/v1/users")
@DriverAdapter
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    Function<User, UserDTO> userDomainToDTOMapper;
    Function<RegisterUserDTO, RegisterUserCommand> createUserDTOToCommandMapper;

    private final GetUserUseCase getUserUseCase;
    private final RemoveUserUseCase removeUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final ChangeUserDetailsUseCase changeUserDetailsUseCase;
    private final ChangeUserStatusUseCase changeUserStatusUseCase;

    @Autowired
    UserController(GetUserUseCase getUserUseCase,
                   RemoveUserUseCase removeUserUseCase,
                   RegisterUserUseCase registerUserUseCase,
                   ChangeUserDetailsUseCase changeUserDetailsUseCase,
                   ChangeUserStatusUseCase changeUserStatusUseCase) {
        this(getUserUseCase,
                removeUserUseCase,
                registerUserUseCase,
                changeUserDetailsUseCase,
                changeUserStatusUseCase,
                UserMappingFactory.createUserDomainToDTOMapper(),
                UserMappingFactory.createUserDTOToCommandMapper());
    }

    UserController(GetUserUseCase getUserUseCase,
                   RemoveUserUseCase removeUserUseCase,
                   RegisterUserUseCase registerUserUseCase,
                   ChangeUserDetailsUseCase changeUserDetailsUseCase,
                     ChangeUserStatusUseCase changeUserStatusUseCase,
                   Function<User, UserDTO> userDomainToDTOMapper,
                   Function<RegisterUserDTO, RegisterUserCommand> createUserDTOToCommandMapper) {
        this.getUserUseCase = getUserUseCase;
        this.removeUserUseCase = removeUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.changeUserDetailsUseCase = changeUserDetailsUseCase;
        this.changeUserStatusUseCase = changeUserStatusUseCase;
        this.userDomainToDTOMapper = userDomainToDTOMapper;
        this.createUserDTOToCommandMapper = createUserDTOToCommandMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> getAll(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Boolean active) {
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
    public ResponseEntity<Void> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        LOGGER.info("Incoming request to create user");

        RegisterUserCommand command = createUserDTOToCommandMapper.apply(registerUserDTO);
        registerUserUseCase.handle(command);
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

    @PatchMapping("/{id}/enabled")
    public ResponseEntity<Void> updateUserStatus(@PathVariable String id, @RequestParam boolean enabled) {
        validateUserAccess(id);
        if(getUserUseCase.existsByIdAndStatus(id, enabled)){
            throw new InvalidStatusChangeException();
        };
        changeUserStatusUseCase.enableUser(id);
        return ResponseEntity.noContent().build();
    }

    private static void validateUserAccess(String id) {
        if (!UserUtils.isAdmin() && !UserUtils.isSelf(id)) {
            LOGGER.warn("Access denied for user: {}", id);
            throw AccessDeniedException.forUser(id);
        }
    }
}
