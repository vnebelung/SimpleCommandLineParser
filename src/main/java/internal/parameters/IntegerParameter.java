/*
 * This file is part of ProDisFuzz, modified on 30.04.25, 20:56.
 * Copyright (c) 2013-2025 Volker Nebelung <vnebelung@prodisfuzz.net>
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

    private final int minInclusive;
    private final int maxInclusive;

    /**
     * Instantiates a new integer parameter.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public IntegerParameter(String name, String description) {
        this(name, description, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Instantiates a new integer parameter. The parameter's values are only allowed in the given range of minimum
     * and maximum values.
     *
     * @param name         the parameter's name
     * @param description  the parameter's description for the help menu
     * @param minInclusive the parameter's allowed minimum value (inclusive)
     * @param maxInclusive the parameter's allowed maximum value (inclusive)
     */
    public IntegerParameter(String name, String description, int minInclusive, int maxInclusive) {
        super(name, description);
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
        if (this.minInclusive > maxInclusive) {
            throw new IllegalArgumentException("minInclusive must not be greater than maxInclusive");
        }
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("The parameter '%s's value must not be null", getName());
        }
        if (value.isBlank()) {
            throw new ParameterException("The parameter '%s's value must not be empty", getName());
        }
        try {
            setCastedValue(Integer.valueOf(value));
        } catch (NumberFormatException ignored) {
            throw new ParameterException("The parameter '%s's value is not a valid integer", getName());
        }
        if (getValue() < minInclusive) {
            throw new ParameterException("The parameter '%s's value must not be lower than %d", getName(),
                    minInclusive);
        }
        if (getValue() > maxInclusive) {
            throw new ParameterException("The parameter '%s's value must not be greater than %d", getName(),
                    maxInclusive);
        }
    }

    @Override
    public AbstractParameter<Integer> copy() {
        IntegerParameter result = new IntegerParameter(getName(), getDescription(), minInclusive, maxInclusive);
        result.setCastedValue(getValue());
        return result;
    }

    @Override
    public String getAllowedValues() {
        if (minInclusive == Integer.MIN_VALUE && maxInclusive == Integer.MAX_VALUE) {
            return "";
        }
        return minInclusive + " to " + maxInclusive;
    }
}
