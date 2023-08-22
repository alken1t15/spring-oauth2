package ru.javabegin.oauth2.spring.test_oauth2.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class CustomAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    public Collection<GrantedAuthority>  convert(Jwt jwt) {
        Map<String, Object> realAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realAccess == null || realAccess.isEmpty()){
            return new ArrayList<>();
        }


        Collection<GrantedAuthority> returnValue = ((List<String>) realAccess.get("roles"))
                .stream().map(roleName ->"ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return returnValue;
    }
}