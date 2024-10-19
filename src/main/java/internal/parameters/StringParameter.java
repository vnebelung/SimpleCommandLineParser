/*
 * This file is part of ProDisFuzz, modified on 19.10.24, 01:50.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is a string parameter of the command line.
 */
public class StringParameter extends AbstractParameter<String> {

    private final Set<String> values = new HashSet<>();

    /**
     * Instantiates a string integer parameter. If there are no given values, the parameter will consider all
     * strings as valid. If the given values contain one or more strings, the parameter will consider only the given
     * strings as valid.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @param values      the parameter's allowed string values
     */
    public StringParameter(String name, String description, String... values) {
        super(name, description);
        for (String value : values) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException("Only non-empty values are allowed");
            }
            this.values.add(value);
        }
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("The parameter's value must not be null");
        }
        if (value.isEmpty()) {
            throw new ParameterException("The parameter's value must not be empty");
        }
        if (!values.isEmpty() && !values.contains(value)) {
            String allowed = "{" + String.join(",", values) + "}";
            throw new ParameterException("The parameter's value is not one of the allowed values " + allowed);
        }
        setCastedValue(value);
    }

    @Override
    public AbstractParameter<String> copy() {
        StringParameter result = new StringParameter(getName(), getDescription(), values.toArray(new String[0]));
        result.setCastedValue(getValue());
        return result;
    }

}
