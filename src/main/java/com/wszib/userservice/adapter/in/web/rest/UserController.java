package com.wszib.userservice.adapter.in.web.rest;

import com.wszib.userservice.adapter.in.web.rest.mapping.UserMappingFactory;
import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.command.CreateUserCommand;
import com.wszib.userservice.domain.error.NullIdException;
import com.wszib.userservice.domain.querry.FilterCriteria;
import com.wszib.userservice.application.usecase.ChangeUserDetailsUseCase;
import com.wszib.userservice.application.usecase.CreateUserUseCase;
import com.wszib.userservice.application.usecase.GetUserUseCase;
import com.wszib.userservice.application.usecase.RemoveUserUseCase;
import com.wszib.userservice.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

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
    public ResponseEntity<List<UserDTO>> getAll(final Optional<String> email,
                                                final Optional<String> firstName,
                                                final Optional<String> lastName,
                                                final Optional<Boolean> active) {
        LOGGER.info("Incoming request to get all users based on filter criteria");

        final FilterCriteria filterCriteria = new FilterCriteria(email, firstName, lastName, active);
        final List<UserDTO> response = getUserUseCase.findAllBy(filterCriteria).stream().map(userDomainToDTOMapper).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        LOGGER.info("Incoming request to get user by id");
        UserDTO response = userDomainToDTOMapper.apply(getUserUseCase.findById(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        LOGGER.info("Incoming request to create user");

        CreateUserCommand command = createUserDTOToCommandMapper.apply(createUserDTO);
        User createdUser = createUserUseCase.create(command);
        return ResponseEntity.ok(userDomainToDTOMapper.apply(createdUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable String id) {
        LOGGER.info("Incoming request to delete user by id");
        removeUserUseCase.remove(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String id,
            @RequestBody ChangeUserDetailsDTO changeUserDetailsDTO) {
        LOGGER.info("Incoming request to update user by id");
        if(id == null) throw new NullIdException();

        ChangeUserDetailsCommand command = UserMappingFactory.createChangeUserDetailsDTOToCommandMapper(id).apply(changeUserDetailsDTO);

        User user = changeUserDetailsUseCase.changeUserDetails(command);
        return ResponseEntity.ok(userDomainToDTOMapper.apply(user));
    }
}
