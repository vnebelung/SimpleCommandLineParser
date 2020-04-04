/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 13:34.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;

/**
 * This class is a string parameter of the command line.
 */
public class StringParameter extends AbstractParameter<String> {

    /**
     * Instantiates a new integer parameter.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public StringParameter(String name, String description) {
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
        setCastedValue(value);
    }

    @Override
    public AbstractParameter<String> copy() {
        StringParameter result = new StringParameter(getName(), getDescription());
        result.setCastedValue(getValue());
        return result;
    }

}
