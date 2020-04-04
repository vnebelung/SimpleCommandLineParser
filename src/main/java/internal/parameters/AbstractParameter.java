/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:47.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.Parameter;
import main.ParameterException;
import main.ParsedParameter;

import java.util.Objects;

/**
 * This class is an abstract parameter of the command line string used in the command line call. The format of this
 * string is as follows: COMMAND SUBCOMMAND --key1 value1 --key2 value2 … The type parameter T is the class of the
 * parameter's value.
 *
 * @param <V> the class of the parameter's value
 */
public abstract class AbstractParameter<V> implements Parameter<V>, ParsedParameter<V> {

    private V value;
    private String description;
    private String name;

    /**
     * Instantiates a new mandatory parameter with a given description.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @throws IllegalArgumentException if the given name or description is null or is blank
     */
    AbstractParameter(String name, String description) {
        if (name == null) {
            throw new IllegalArgumentException("The parameter's name must not be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("The parameter's name must not be empty");
        }
        if (name.contains("--")) {
            throw new IllegalArgumentException("The parameter's name must not contain two consecutive dashes");
        }
        if (description == null) {
            throw new IllegalArgumentException("The parameter's description must not be empty");
        }
        if (description.isBlank()) {
            throw new IllegalArgumentException("The parameter's description must not be empty");
        }
        this.name = name;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public V getValue() {
        return value;
    }

    /**
     * Sets the value of the parameter. If the given value cannot be cast to the parameter's type, a
     * ParameterException is thrown.
     *
     * @param value the new parameter's value
     * @throws ParameterException if the given value cannot be cast to the parameter's type
     */
    public abstract void setValue(String value) throws ParameterException;

    /**
     * Sets the value of the parameter to the given value.
     *
     * @param value the parameter's value
     */
    void setCastedValue(V value) {
        this.value = value;
    }

    @Override
    public boolean isOptional() {
        return value != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractParameter<V> makeOptional(V value) {
        if (value == null) {
            throw new IllegalArgumentException("Default value must not be null");
        }
        this.value = value;
        return this;
    }

    /**
     * Returns a copy of this command, that is a newly instantiated command with the same name, the same description,
     * and the same parameters. Note that subcommands are not copied.
     *
     * @return the copied command
     */
    public abstract AbstractParameter<V> copy();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractParameter)) {
            return false;
        }
        AbstractParameter<?> that = (AbstractParameter<?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
