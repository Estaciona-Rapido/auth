package org.estacionarapido.service;

import java.time.temporal.ChronoUnit;

import org.estacionarapido.exception.NoAdminException;
import org.estacionarapido.exception.WrongPasswordException;
import org.estacionarapido.persistence.UserEntity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {
    @Inject
    EntityManager entityManager;

    private UserEntity getAdmin() throws NoAdminException
    {
        UserEntity admin = entityManager.find(UserEntity.class, 1);
        if (admin == null) {
            throw new NoAdminException();
        } else {
            return admin;
        }
    }

    @Transactional
    @RateLimit(value = 5, window = 5, windowUnit = ChronoUnit.MINUTES)
    public String login(String password) throws NoAdminException, WrongPasswordException
    {
        UserEntity userEntity = getAdmin();
        if (BcryptUtil.matches(password, userEntity.passwordHash)) {
            return Jwt
            .issuer("estacionarapido-demo")
            .upn("admin")
            .groups("admin")
            .sign();
        } else {
            throw new WrongPasswordException();
        }
    }

    @Transactional
    @RateLimit(value = 5, window = 5, windowUnit = ChronoUnit.MINUTES)
    public void changePassword(String oldPassword, String newPassword) throws NoAdminException, WrongPasswordException
    {
        UserEntity userEntity = getAdmin();
        if (BcryptUtil.matches(oldPassword, userEntity.passwordHash)) {
        userEntity.passwordHash = BcryptUtil.bcryptHash(newPassword);
        } else {
            throw new WrongPasswordException();
        }
    }
}
