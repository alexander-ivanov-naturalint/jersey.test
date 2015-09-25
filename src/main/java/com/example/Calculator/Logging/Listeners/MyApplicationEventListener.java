package com.example.Calculator.Logging.Listeners;

import com.example.Calculator.Logging.Log;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 * Created by ivaa on 9/25/2015.
 */
public class MyApplicationEventListener implements ApplicationEventListener {

    @Override
    public void onEvent(ApplicationEvent event)
    {

    }

    @Override
    public RequestEventListener onRequest(final RequestEvent requestEvent)
    {
        String out = String.format("%s request to %s was received."
                , requestEvent.getContainerRequest().getMethod()
                , requestEvent.getContainerRequest().getRequestUri().toString()
        );

        Log.logger.info(out);

        return null;
    }

}
