/*
 * This file is part of ProDisFuzz, modified on 12.10.24, 22:18.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;

/**
 * This class is a boolean parameter of the command line.
 */
public class BooleanParameter extends AbstractParameter<Boolean> {

    /**
     * Instantiates a new boolean parameter.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public BooleanParameter(String name, String description) {
        super(name, description);
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("The parameter's value must not be null");
        }
        if (value.isBlank()) {
            throw new ParameterException("The parameter's value must not be empty");
        }
        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            throw new ParameterException("The parameter's value is not a valid boolean");
        }
        setCastedValue(Boolean.valueOf(value.toLowerCase()));
    }

    @Override
    public AbstractParameter<Boolean> copy() {
        BooleanParameter result = new BooleanParameter(getName(), getDescription());
        result.setCastedValue(getValue());
        return result;
    }

}
