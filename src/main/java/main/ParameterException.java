/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 09:54.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import java.util.IllegalFormatException;

/**
 * This class is a parameter exception that is thrown when a parameter could not be cast into the expected class.
 */
public class ParameterException extends Exception {

    /**
     * Instantiates a new parameter exception with a given parameter value that could not be cast to the parameter's
     * class of the parameter type.
     *
     * @param format A format string
     * @param args   Arguments referenced by the format specifiers in the format string. If there are more
     *               arguments than format specifiers, the extra arguments are ignored. The number of arguments is
     *               variable and may be zero. The maximum number of arguments is limited by the maximum dimension of
     *               a Java array as defined by The Java™ Virtual Machine Specification. The behaviour on a null
     *               argument depends on the conversion.
     * @throws IllegalFormatException If a format string contains an illegal syntax, a format specifier that is
     *                                incompatible with the given arguments, insufficient arguments given the format
     *                                string, or other illegal conditions. For specification of all possible
     *                                formatting errors, see the Details section of the formatter class specification.
     */
    public ParameterException(String format, Object... args) {
        super(String.format(format, args));
    }
}
