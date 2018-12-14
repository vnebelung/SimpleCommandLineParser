/*
 * This file is part of ProDisFuzz, modified on 12/14/18 6:19 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This class is a parameter exception that is thrown when a parameter could not be cast into the expected class.
 */
public class ParameterException extends Exception {

    /**
     * Instantiates a new parameter exception with a given parameter value that could not be cast to the parameter's
     * class of the parameter type.
     *
     * @param value the paeameter value that could not be cast
     */
    public ParameterException(String value) {
        super(value);
    }
}
