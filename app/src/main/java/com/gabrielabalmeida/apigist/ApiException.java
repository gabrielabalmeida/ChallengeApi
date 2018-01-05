package com.gabrielabalmeida.apigist;

/**
 * Created by gabriela on 04/01/18.
 */

public class ApiException extends Exception {

    public ApiException() {
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}
