package ru.javabegin.oauth2.spring.test_oauth2.keycloak;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakUtils {
    @Value("${keycloak.auth-server-url}")
    private  String serverURL;
    @Value("${keycloak.realm}")
    private  String realm;
    @Value("${keycloak.resource}")
    private  String clientID;
    @Value("${keycloak.credentials.secret}")
    private  String clientSecret;

    private static Keycloak keycloak;
    private static  RealmResource realmResource;

    private static UsersResource userResource;

    @PostConstruct
    private Keycloak initKeycloak(){
        System.out.println(serverURL);
        if (keycloak == null){
            keycloak = KeycloakBuilder.builder()
                    .realm(realm)
                    .serverUrl(serverURL)
                    .clientId(clientID)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();

            realmResource = keycloak.realm(realm);

            userResource = realmResource.users();
        }
        return keycloak;
    }

    public Response createKeycloakUser(String username, String password){
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(username);
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setEmail(username);
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Response response = userResource.create(kcUser);

        return response;
    }



    private CredentialRepresentation createPasswordCredentials(String password){
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public void addRoles(String userId, List<String> roles) {
        List<RoleRepresentation> kcRoles = new ArrayList<>();

        for(String role : roles){
            RoleRepresentation roleRap = realmResource.roles().get(role).toRepresentation();
            kcRoles.add(roleRap);
        }

        UserResource uniqueUserResource = userResource.get(userId);

        uniqueUserResource.roles().realmLevel().add(kcRoles);
    }
}
