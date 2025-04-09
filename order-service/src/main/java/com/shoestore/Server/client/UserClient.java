package com.shoestore.Server.client;


import com.shoestore.Server.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8765/auth")
public interface UserClient {
    @GetMapping("/getListCusCustom")
    List<UserResponseDTO> getListCusCustom();

    @GetMapping("/getListCusCustom/{id}")
    List<UserResponseDTO> getListCusCustomByID(@PathVariable int id) ;

    @GetMapping("/users/{id}")
    List<UserResponseDTO> getUser(@PathVariable int id);
}
