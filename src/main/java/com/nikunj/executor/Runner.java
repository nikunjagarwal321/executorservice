package com.nikunj.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    ExecutorServiceImpl executorServiceImpl;

    @Override
    public void run(String... args) {
        executorServiceImpl.singleThreadFunc();
        executorServiceImpl.mutliThreadIOFunc();
        executorServiceImpl.multiThreadCPUFunc();
        executorServiceImpl.multiThreadReturnableFunc();
        executorServiceImpl.threadpoolExecFunc();

    }
}
