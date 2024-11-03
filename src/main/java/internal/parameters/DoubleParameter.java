/*
 * This file is part of ProDisFuzz, modified on 26.10.24, 23:24.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;

/**
 * This class is a double parameter of the command line.
 */
public class DoubleParameter extends AbstractParameter<Double> {

    private final double minInclusive;
    private final double maxInclusive;

    /**
     * Instantiates a new double parameter.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public DoubleParameter(String name, String description) {
        this(name, description, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Instantiates a new double parameter. The parameter's values are only allowed in the given range of minimum
     * and maximum values.
     *
     * @param name         the parameter's name
     * @param description  the parameter's description for the help menu
     * @param minInclusive the parameter's allowed minimum value (inclusive)
     * @param maxInclusive the parameter's allowed maximum value (inclusive)
     */
    public DoubleParameter(String name, String description, double minInclusive, double maxInclusive) {
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
            throw new ParameterException("The parameter's value must not be null");
        }
        if (value.isBlank()) {
            throw new ParameterException("The parameter's value must not be empty");
        }
        try {
            setCastedValue(Double.valueOf(value));
        } catch (NumberFormatException ignored) {
            throw new ParameterException("The parameter's value is not a valid double");
        }
        if (getValue() < minInclusive) {
            throw new ParameterException("The parameter's value must not be lower than " + minInclusive);
        }
        if (getValue() > maxInclusive) {
            throw new ParameterException("The parameter's value must not be greater than " + maxInclusive);
        }
    }

    @Override
    public AbstractParameter<Double> copy() {
        DoubleParameter result = new DoubleParameter(getName(), getDescription(), minInclusive, maxInclusive);
        result.setCastedValue(getValue());
        return result;
    }

    @Override
    public String getAllowedValues() {
        if (minInclusive == -Double.MAX_VALUE && maxInclusive == Double.MAX_VALUE) {
            return "";
        }
        return minInclusive + " to " + maxInclusive;
    }
}