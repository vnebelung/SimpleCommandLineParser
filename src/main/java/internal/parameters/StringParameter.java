/*
 * This file is part of ProDisFuzz, modified on 30.04.25, 20:35.
 * Copyright (c) 2013-2025 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class is a string parameter of the command line.
 */
public class StringParameter extends AbstractParameter<String> {

    private final SortedSet<String> allowedValues = new TreeSet<>();

    /**
     * Instantiates a new string parameter. If there are no given values, the parameter will consider all strings as
     * valid. If the given values contain one or more strings, the parameter will consider only the given strings as
     * valid.
     *
     * @param name          the parameter's name
     * @param description   the parameter's description for the help menu
     * @param allowedValues the parameter's allowed string values
     */
    public StringParameter(String name, String description, String... allowedValues) {
        super(name, description);
        for (String allowedValue : allowedValues) {
            if (allowedValue == null) {
                throw new IllegalArgumentException("Allowed values must not be null");
            }
            if (allowedValue.isEmpty()) {
                throw new IllegalArgumentException("Allowed values must not be empty");
            }
            this.allowedValues.add(allowedValue);
        }
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("The parameter '%s's value must not be null", getName());
        }
        if (value.isEmpty()) {
            throw new ParameterException("The parameter '%s's value must not be empty", getName());
        }
        if (!allowedValues.isEmpty() && !allowedValues.contains(value)) {
            String allowed = "{" + String.join(",", allowedValues) + "}";
            throw new ParameterException("The parameter '%s's value is not one of the allowed values %s", getName(),
                    allowed);
        }
        setCastedValue(value);
    }

    @Override
    public AbstractParameter<String> copy() {
        StringParameter result = new StringParameter(getName(), getDescription(), allowedValues.toArray(new String[0]));
        result.setCastedValue(getValue());
        return result;
    }

    @Override
    public String getAllowedValues() {
        return String.join("|", allowedValues);
    }
}
