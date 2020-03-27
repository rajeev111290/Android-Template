package com.roundlers.mytemplate.models.exceptions;


import com.roundlers.mytemplate.models.Zeus;

/**
 * Created by abhayalekal on 29/11/17.
 */

public class NoConnectionException extends Zeus {
    public NoConnectionException() {
        super("Please connect to internet", ErrorConstants.NO_INTERNET);
    }
}
