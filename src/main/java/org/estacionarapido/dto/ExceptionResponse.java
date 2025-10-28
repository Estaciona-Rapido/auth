package org.estacionarapido.dto;

import java.time.OffsetDateTime;

public record ExceptionResponse(String errorMessage, OffsetDateTime at) {}
