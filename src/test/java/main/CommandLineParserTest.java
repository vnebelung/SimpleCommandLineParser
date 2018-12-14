/*
 * This file is part of ProDisFuzz, modified on 12/14/18 6:19 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import commands.Subcommand;
import org.testng.annotations.Test;
import parameters.IntegerParameter;
import parameters.StringParameter;

import static org.testng.Assert.*;

public class CommandLineParserTest {

    @Test
    public void testCreateCommand() {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("testname", "testdescription");
        assertEquals(commandLineParser.getCommand().getName(), "testname");
        assertEquals(commandLineParser.getCommand().getDescription(), "testdescription");
    }

    @Test
    public void testGetCommand1() {
        CommandLineParser commandLineParser = new CommandLineParser();
        assertNull(commandLineParser.getCommand());

    }

    @Test
    public void testGetCommand2() {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("testname", "testdescriptipn");
        assertNotNull(commandLineParser.getCommand());
    }

    @Test
    public void testParse1() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "--parameter1=string", "--parameter2=6");
    }

    @Test
    public void testParse2() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse3() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "--parameter1=dummy");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse4() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "--parameter1=", "--parameter4=2");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse5() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter3", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter4", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("--parameter3=value", "--parameter4=6", "subcommand1");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse7() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "parameter1=string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse8() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "-parameter1=string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse9() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "--=string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse10() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse("subcommand1", "--parameter1 string", "--parameter2=6");
    }

    @Test
    public void testParse11() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        commandLineParser.getCommand().add(new StringParameter("parameter1", "description", "dummy"));
        commandLineParser.getCommand().add(new IntegerParameter("parameter2", "description"));

        commandLineParser.parse("--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse12() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        commandLineParser.getCommand().add(new StringParameter("parameter1", "description", "dummy"));
        commandLineParser.getCommand().add(new IntegerParameter("parameter2", "description"));

        commandLineParser.parse("subcommand1", "--parameter1 string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse13() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        commandLineParser.getCommand().add(new StringParameter("parameter1", "description", "dummy"));
        commandLineParser.getCommand().add(new IntegerParameter("parameter2", "description"));

        commandLineParser.parse("--parameter1=dummy");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse14() throws ParameterException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        commandLineParser.getCommand().add(subcommand1);

        commandLineParser.parse();
    }

}
