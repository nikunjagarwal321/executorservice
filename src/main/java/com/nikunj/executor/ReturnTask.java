package com.nikunj.executor;

import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnTask implements Callable{
    Logger log = LoggerFactory.getLogger(Task.class);
    @Override
    public Integer call() {
        try {
            log.info("Thread {} is going to sleep ", Thread.currentThread().getName());
            Thread.sleep(1000);
            log.info("Thread {} task execution complete", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            log.error("Error : ", e);
        }
        return (int)Thread.currentThread().getId();
    }
    
}
