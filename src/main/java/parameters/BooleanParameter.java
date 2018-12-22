/*
 * This file is part of ProDisFuzz, modified on 12/21/18 6:01 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

import main.ParameterException;

/**
 * This class represents a boolean parameter of the command line.
 */
public class BooleanParameter extends AbstractParameter<Boolean> {

    /**
     * Instantiates a new mandatory boolean parameter.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public BooleanParameter(String name, String description) {
        super(name, description);
    }

    /**
     * Instantiates a new optional boolean parameter.
     *
     * @param name         the parameter's name
     * @param description  the parameter's description for the help menu
     * @param defaultValue the parameter's default value
     */
    public BooleanParameter(String name, String description, boolean defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value.isBlank()) {
            throw new ParameterException("The parameter's value must not be empty");
        }
        if (!value.toLowerCase().equals("true") && !value.toLowerCase().equals("false")) {
            throw new ParameterException("The parameter's value is not a valid boolean");
        }
        setInternalValue(Boolean.valueOf(value));
    }

    @Override
    public Parameter<Boolean> copy() {
        return getValue() == null ? new BooleanParameter(getName(), getDescription()) :
                new BooleanParameter(getName(), getDescription(), getValue());
    }


}
