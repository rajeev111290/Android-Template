package com.roundlers.mytemplate.models.exceptions;

import com.roundlers.mytemplate.models.Zeus;

/**
 * Created by abhayalekal on 29/11/17.
 */

public class NoDataException extends Zeus {
    public NoDataException() {
        super("No More Data", ErrorConstants.NO_DATA);
    }
}
