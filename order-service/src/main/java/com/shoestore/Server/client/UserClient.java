package com.shoestore.Server.client;


import com.shoestore.Server.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8765/auth")
public interface UserClient {
    @GetMapping("getListCusForLoyal")
    List<UserResponseDTO> getListCusForLoyal();

}
