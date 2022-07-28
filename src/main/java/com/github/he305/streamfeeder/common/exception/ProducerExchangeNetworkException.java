package com.github.he305.streamfeeder.common.exception;

public class ProducerExchangeNetworkException extends ProducerExchangeException {
    public ProducerExchangeNetworkException(String mes) {
        super("HTTP error: " + mes);
    }
}
