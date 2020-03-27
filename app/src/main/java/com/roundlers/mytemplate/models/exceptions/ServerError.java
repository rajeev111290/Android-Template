package com.roundlers.mytemplate.models.exceptions;

import com.roundlers.mytemplate.models.Zeus;

/**
 * Created by abhayalekal on 29/11/17.
 */

public class ServerError extends Zeus {

    public ServerError() {
        super("Failed to connect to server", ErrorConstants.SERVER_ERROR);
    }
}
