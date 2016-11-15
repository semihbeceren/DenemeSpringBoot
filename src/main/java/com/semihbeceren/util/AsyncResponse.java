package com.semihbeceren.util;

import java.util.concurrent.*;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
public class AsyncResponse<V> implements Future<V> {

    private V value;
    private Exception executionException;
    private boolean isCompletedExceptionally = false;
    private boolean isCancelled = false;
    private boolean isDone = false;
    private long checkCompletedInterval = 100;

    public AsyncResponse(){}

    public AsyncResponse(V value){
        this.value = value;
        this.isDone = true;
    }

    public AsyncResponse(Throwable exception){
        this.executionException = new ExecutionException(exception);
        this.isCompletedExceptionally = true;
        this.isDone = true;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        this.isDone = true;
        return false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    public boolean isCompletedExceptionally(){
        return this.isCompletedExceptionally;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        block(0);
        if(isCancelled()){
            throw new CancellationException();
        }

        if(isCompletedExceptionally()){
            throw new ExecutionException(this.executionException);
        }

        if(isDone()){
            return this.value;
        }

        throw new InterruptedException();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long timeoutInMillis = unit.toMillis(timeout);
        block(timeoutInMillis);

        if(isCancelled()){
            throw new CancellationException();
        }

        if(isCompletedExceptionally()){
            throw new ExecutionException(this.executionException);
        }

        if(isDone()){
            return this.value;
        }

        throw new InterruptedException();
    }

    public boolean complete(V value){
        this.value = value;
        this.isDone = true;

        return true;
    }


    public boolean completeExceptionally(Throwable exception){
        this.value = null;
        this.executionException = new ExecutionException(exception);
        this.isCompletedExceptionally = true;
        this.isDone = true;

        return true;
    }

    public void setCheckCompletedInterval(long millis){
        this.checkCompletedInterval = millis;
    }

    private void block(long timeout)throws InterruptedException{
        long start = System.currentTimeMillis();

        while (!isDone() && !isCancelled()){
            if(timeout > 0){
                if(System.currentTimeMillis() > start + timeout){
                    break;
                }
            }

            Thread.sleep(checkCompletedInterval);
        }
    }
}
