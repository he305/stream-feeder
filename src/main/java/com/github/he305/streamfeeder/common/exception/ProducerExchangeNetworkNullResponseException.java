package com.github.he305.streamfeeder.common.exception;

public class ProducerExchangeNetworkNullResponseException extends ProducerExchangeNetworkException {
    public ProducerExchangeNetworkNullResponseException(String url) {
        super("Response, retrieved from " + url + " is null");
    }
}
