/*
 * This file is part of ProDisFuzz, modified on 23.10.24, 21:24.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is a path parameter of the command line.
 */
public class PathParameter extends AbstractParameter<Path> {

    private final boolean existing;

    /**
     * Instantiates a path parameter. This parameter contains a string that is a valid path for the operating system.
     * The given value existing shall be set to true if the path shall point to an already existing file or directory.
     * If the path shall point to a non-existing directory, the value must beset to false.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @param existing    true if the path shall point to an existing file/directory
     */
    public PathParameter(String name, String description, boolean existing) {
        super(name, description);
        this.existing = existing;
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("The parameter's value must not be null");
        }
        if (value.isEmpty()) {
            throw new ParameterException("The parameter's value must not be empty");
        }
        Path path = Path.of(value).toAbsolutePath().normalize();
        if (existing && !Files.exists(path)) {
            throw new ParameterException("The parameter's value must point to an existing file or directory");
        }
        if (!existing && Files.exists(path)) {
            throw new ParameterException("The parameter's value must point to a non-existing file or directory");
        }
        setCastedValue(path);
    }

    @Override
    public AbstractParameter<Path> copy() {
        PathParameter result = new PathParameter(getName(), getDescription(), existing);
        result.setCastedValue(getValue());
        return result;
    }

    @Override
    public String getAllowedValues() {
        return "";
    }
}
