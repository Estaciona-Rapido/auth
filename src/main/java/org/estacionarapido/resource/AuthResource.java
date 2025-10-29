package org.estacionarapido.resource;

import org.estacionarapido.dto.ChangePasswordProposal;
import org.estacionarapido.exception.NoAdminException;
import org.estacionarapido.exception.WrongPasswordException;
import org.estacionarapido.service.AuthService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("")
@ApplicationScoped
public class AuthResource {

    @Inject
    AuthService authService;

    /**
     * @param loginProposal
     * @return a JWT token
     */
    @POST
    @Path("login")
    public String login(String password) {
        if (password.length() < 8 || password.length() > 64) {
            throw new BadRequestException("Passwords must be between 8 and 64 characters.");
        }
        try {
            return authService.login(password);
        } catch (NoAdminException noAdminException) {
            throw EstacionaRapidoExceptionResponse.create(Response.Status.INTERNAL_SERVER_ERROR, noAdminException.getMessage());
        } catch (WrongPasswordException wrongPasswordException) {
            throw EstacionaRapidoExceptionResponse.create(Response.Status.UNAUTHORIZED, wrongPasswordException.getMessage());
        }
    }

    @PATCH
    @Path("configuration/admin/change-password")
    public void changeAdminPassword(ChangePasswordProposal changePasswordProposal) throws NoAdminException, WrongPasswordException
    {
        int oldPasswordLength = changePasswordProposal.oldPassword().length();
        int newPasswordLength = changePasswordProposal.newPassword().length();
        if (oldPasswordLength < 8 || oldPasswordLength > 64 || newPasswordLength < 8 || newPasswordLength > 64) {
            throw new BadRequestException("Passwords must be between 8 and 64 characters.");
        }
        try {
            authService.changePassword(changePasswordProposal.oldPassword(), changePasswordProposal.newPassword());
        } catch (NoAdminException noAdminException) {
            throw EstacionaRapidoExceptionResponse.create(Response.Status.INTERNAL_SERVER_ERROR, noAdminException.getMessage());
        } catch (WrongPasswordException wrongPasswordException) {
            throw EstacionaRapidoExceptionResponse.create(Response.Status.UNAUTHORIZED, wrongPasswordException.getMessage());
        }
    }


}
