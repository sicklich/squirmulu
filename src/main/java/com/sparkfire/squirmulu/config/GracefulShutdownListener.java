package com.sparkfire.squirmulu.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class GracefulShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(GracefulShutdownListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("closing");
        logger.info("Application context is closing, performing graceful shutdown...");
        // 在这里添加你需要在优雅关闭时执行的其他操作，例如清理资源等
    }
}
