package com.epam.esm.controller.rest;

import com.epam.esm.model.service.UserService;
import com.epam.esm.model.service.dto.UserDTO;
import com.epam.esm.model.service.exception.NotExistEntityException;
import com.epam.esm.model.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable(name = "id") Long id)
            throws ServiceException, NotExistEntityException {
        UserDTO userDTO = userService.find(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity orderCertificate(@PathVariable(name = "id") Long id, @RequestBody Long idCertificate) throws ServiceException {
        userService.orderCertificate(id, idCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
