package ru.javabegin.oauth2.spring.test_oauth2.controller;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.oauth2.spring.test_oauth2.dto.UserDTO;
import ru.javabegin.oauth2.spring.test_oauth2.keycloak.KeycloakUtils;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admin")
@RestController
public class Test {
    private final KeycloakUtils keycloakUtils;

    public Test(KeycloakUtils keycloakUtils) {
        this.keycloakUtils = keycloakUtils;
    }

    @GetMapping("/delete/{id}")
//    @PreAuthorize("hasRole('admin')")
    public String work(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        System.out.println("jwt.getClaim 'email'" + jwt.getClaim("email"));
        System.out.println("jwt.subject" + jwt.getSubject());
        System.out.println("id delete = " + id);
        return "delete work";
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('admin')")
    public String internal(@RequestBody UserDTO userDTO){
      Response response= keycloakUtils.createKeycloakUser(userDTO.getEmail(), userDTO.getPassword());

        if (response.getStatus()==409){
            System.out.println("error");
        }

        String userId= CreatedResponseUtil.getCreatedId(response);
        System.out.println("id: " + userId);

        List<String> defaultRoles = new ArrayList<>();
        defaultRoles.add("user");
        defaultRoles.add("admin");
        keycloakUtils.addRoles(userId,defaultRoles);

        return "add work";
    }
//    @PreAuthorize("hasRole('admin')")
//    и
//    @PreAuthorize("hasAuthority('ROLE_admin')")

}
