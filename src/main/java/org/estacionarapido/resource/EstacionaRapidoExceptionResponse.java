package org.estacionarapido.resource;

import java.time.OffsetDateTime;

import org.estacionarapido.dto.ExceptionResponse;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class EstacionaRapidoExceptionResponse {
    public static WebApplicationException create(Response.Status httpStatus, String message)
    {
        return new WebApplicationException(Response.status(httpStatus)
            .entity(new ExceptionResponse(message, OffsetDateTime.now()))
            .build());
    }
}