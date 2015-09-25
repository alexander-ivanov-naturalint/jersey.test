package com.example.Calculator.Logging.Listeners;

import com.example.Calculator.Logging.Log;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by ivaa on 9/25/2015.
 */
public class MyExceptionMapper implements ExceptionMapper {

    public Response toResponse(Throwable E)
    {
        return null;
    }
}
