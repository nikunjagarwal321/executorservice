package com.nikunj.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task implements Runnable {
    Logger log = LoggerFactory.getLogger(Task.class);

    @Override
    public void run(){
        try {
            log.info("Thread {} is going to sleep ", Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Error : ", e);
        }
    }   
    
}
