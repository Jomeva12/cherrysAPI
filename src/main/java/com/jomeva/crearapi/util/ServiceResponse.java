package com.jomeva.crearapi.util;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    private boolean error;

    private String message;

    private T response;

}
