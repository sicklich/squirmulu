package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.SquirmuluApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartAppListener {
    private static final Logger logger = LoggerFactory.getLogger(StartAppListener.class);
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        // Your startup logic here
        logger.info("Application is ready");
    }
}
