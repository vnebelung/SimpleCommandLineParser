/*
 * This file is part of ProDisFuzz, modified on 3/7/19 12:23 AM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;

/**
 * This class is an integer parameter of the command line.
 */
public class IntegerParameter extends AbstractParameter<Integer> {

    /**
     * Instantiates a new integer parameter.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public IntegerParameter(String name, String description) {
        super(name, description);
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value.isBlank()) {
            throw new ParameterException("The parameter's value must not be empty");
        }
        try {
            setInternalValue(Integer.valueOf(value));
        } catch (NumberFormatException ignored) {
            throw new ParameterException("The parameter's value is not a valid integer");
        }
    }

}
