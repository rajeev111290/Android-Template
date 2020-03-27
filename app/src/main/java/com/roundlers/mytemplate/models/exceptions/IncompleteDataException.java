package com.roundlers.mytemplate.models.exceptions;

/**
 * Created by abhayalekal on 04/01/18.
 */

public class IncompleteDataException extends RuntimeException {
    public IncompleteDataException(String s) {
        super(s);
    }

    public IncompleteDataException() {
        super();
    }
}
