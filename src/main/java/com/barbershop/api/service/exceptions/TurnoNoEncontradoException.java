package com.barbershop.api.service.exceptions;

public class TurnoNoEncontradoException  extends RuntimeException  {
    
     public  TurnoNoEncontradoException(final String message) {
        super(message);
    }

    public TurnoNoEncontradoException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
