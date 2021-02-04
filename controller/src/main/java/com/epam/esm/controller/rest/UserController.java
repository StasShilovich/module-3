package com.epam.esm.controller.rest;

import com.epam.esm.model.service.OrderService;
import com.epam.esm.model.service.UserService;
import com.epam.esm.model.service.dto.TopUserInfo;
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
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable(name = "id") Long id)
            throws ServiceException, NotExistEntityException {
        UserDTO userDTO = userService.find(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity orderCertificate(@PathVariable(name = "id") Long id, @RequestBody Long idCertificate)
            throws ServiceException {
        orderService.orderCertificate(id, idCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/top/info")
    public ResponseEntity<TopUserInfo> getTopUserInfo() throws ServiceException {
        TopUserInfo topUserInfo = orderService.getTopUserInfo();
        return ResponseEntity.ok(topUserInfo);
    }
}
