/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:47.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CommandFactoryTest {

    @Test
    public void testCreateCommand() {
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        assertEquals(command.getName(), "commandname");
        assertEquals(command.getDescription(), "commanddescription");
    }

    @Test
    public void testCreateSubCommand() {
        Subcommand subcommand = CommandFactory.createSubcommand("subcommandname", "subcommanddescription");
        assertEquals(subcommand.getName(), "subcommandname");
        assertEquals(subcommand.getDescription(), "subcommanddescription");
    }
}
