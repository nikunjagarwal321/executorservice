package com.nikunj.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExecutorServiceImpl {
    static Logger log = LoggerFactory.getLogger(Task.class);

    public void singleThreadFunc() {
        log.info("Inside function : singleThreadFunc");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Task());
        safeShutdown(executorService);
    }

    public void mutliThreadIOFunc() {
        log.info("Inside function : mutliThreadIOFunc");
        // For IO Bound tasks(DB ops, Http calls etc), create thread pool of required
        // fixed size
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Task());
        }
        safeShutdown(executorService);
    }

    public void multiThreadCPUFunc() {
        log.info("Inside function : multiThreadCPUFunc");
        // For CPU Bound tasks, create thread pool of size equal to no of processors.
        // Otherwise, context switching will be there.
        int cores = Runtime.getRuntime().availableProcessors();
        log.info("No. of available cores = {}", cores);
        ExecutorService executorService = Executors.newFixedThreadPool(cores);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Task());
        }
        safeShutdown(executorService);
    }

    public void multiThreadReturnableFunc() {
        log.info("Inside function : multiThreadReturnableFunc");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<Integer>> futures = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = executorService.submit(new ReturnTask());
            futures.add(future);
        }
        for (int i = 0; i < 10; i++) {
            Integer res;
            try {
                res = futures.get(i).get();
                log.info("Value returned from Task : {}", res);
                result.add(res);
            } catch (InterruptedException e) {
                log.error("Error : ", e);
                e.printStackTrace();
            } catch (ExecutionException e) {
                log.error("Error : ", e);
            }
        }
        safeShutdown(executorService);
    }

    public void threadpoolExecFunc() {
        log.info("Inside function : threadpoolExecFunc");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Task());
        }
        safeShutdown(threadPoolExecutor);
    }

    private void safeShutdown(ExecutorService executorService) {
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Error : ", e);
            }
        }
    }

}
