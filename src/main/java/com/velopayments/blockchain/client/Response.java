package com.velopayments.blockchain.client;

public abstract class Response {
    public abstract long getRequestOffset();
    public abstract RequestType getRequestType();
}
