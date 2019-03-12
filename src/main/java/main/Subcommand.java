/*
 * This file is part of ProDisFuzz, modified on 3/7/19 11:15 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a subcommand of the command line string, which is as follows: COMMAND SUBCOMMAND
 * --key1=value1 --key2=value2 ...
 */
public interface Subcommand {

    /**
     * Adds a parameter to this subcommand.
     *
     * @param parameter the parameter to be added
     */
    void add(Parameter<?> parameter);

}
