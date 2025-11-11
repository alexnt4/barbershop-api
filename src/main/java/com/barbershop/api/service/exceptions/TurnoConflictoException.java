package com.barbershop.api.service.exceptions;

public class TurnoConflictoException  extends RuntimeException {

    public  TurnoConflictoException(final String message) {
        super(message);
    }

    public TurnoConflictoException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
