/*
 * This file is part of ProDisFuzz, modified on 3/12/19 1:03 AM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import internal.commands.InternalSubcommand;
import internal.parameters.StringParameter;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class CommandLineTest {

    @Test
    public void testCreateSubcommand() {
        CommandLine commandLine = new CommandLine("", "");
        assertNotNull(commandLine.createSubcommand("subommandname", "subcommanddescription"));
    }

    @Test
    public void testCreateBooleanParameter() {
        CommandLine commandLine = new CommandLine("", "");
        assertNotNull(commandLine.createBooleanParameter("", ""));

    }

    @Test
    public void testCreateIntegerParameter() {
        CommandLine commandLine = new CommandLine("", "");
        assertNotNull(commandLine.createIntegerParameter("", ""));

    }

    @Test
    public void testCreateStringParameter() {
        CommandLine commandLine = new CommandLine("", "");
        assertNotNull(commandLine.createStringParameter("", ""));

    }

    @Test
    public void testGetCommand1() {
        CommandLine commandLine = new CommandLine("", "");
        assertNotNull(commandLine.getCommand());
    }

    @Test
    public void testParse() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        Subcommand subcommand1 = commandLine.createSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        subcommand1.add(commandLine.createIntegerParameter("parameter2name", "parameter2description"));
        subcommand1.add(commandLine.createBooleanParameter("parameter3name", "parameter3description"));
        commandLine.getCommand().add(subcommand1);
        Subcommand subcommand2 = commandLine.createSubcommand("subcommand2name", "subcommand2description");
        Parameter<String> parameter4 = commandLine.createStringParameter("parameter4name", "parameter4description");
        parameter4.setDefaultValue("dummy");
        subcommand2.add(parameter4);
        subcommand2.add(commandLine.createIntegerParameter("parameter5name", "parameter5description"));
        subcommand2.add(commandLine.createBooleanParameter("parameter6name", "parameter6description"));
        commandLine.getCommand().add(subcommand2);

        ParsedCommand parsedCommand = commandLine
                .parse("subcommand1name", "--parameter1name=parameter1value", "--parameter2name=6",
                        "--parameter3name=true");

        assertEquals(parsedCommand.getName(), "commandname");

        assertNotNull(parsedCommand.getSubcommand());
        assertEquals(parsedCommand.getSubcommand().getName(), "subcommand1name");

        assertEquals(parsedCommand.getSubcommand().getStringParameter("parameter1name").getValue(), "parameter1value");
        assertEquals(parsedCommand.getSubcommand().getIntegerParameter("parameter2name").getValue().intValue(), 6);
        assertTrue(parsedCommand.getSubcommand().getBooleanParameter("parameter3name").getValue());
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse1() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        Subcommand subcommand1 = new InternalSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        Parameter<Boolean> parameter2 = commandLine.createBooleanParameter("parameter2name", "parameter2description");
        subcommand1.add(parameter2);
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1name", "--parameter1name=parameter1value");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse2() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        InternalSubcommand subcommand1 = new InternalSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        Parameter<Integer> parameter2 = commandLine.createIntegerParameter("parameter2name", "parameter2description");
        subcommand1.add(parameter2);
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1name", "--parameter1name=", "--parameter3name=2");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse3() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        InternalSubcommand subcommand1 = new InternalSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        Parameter<Integer> parameter2 = commandLine.createIntegerParameter("parameter2name", "parameter2description");
        subcommand1.add(parameter2);
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("--parameter1name=parameter1value", "--parameter2name=6", "subcommand1name");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse4() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        InternalSubcommand subcommand1 = new InternalSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        Parameter<Integer> parameter2 = commandLine.createIntegerParameter("parameter2name", "parameter2description");
        subcommand1.add(parameter2);
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1name", "parameter1name=parameter1value", "--parameter2name=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse5() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        InternalSubcommand subcommand1 = new InternalSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        Parameter<Integer> parameter2 = commandLine.createIntegerParameter("parameter2name", "parameter2description");
        subcommand1.add(parameter2);
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1name", "-parameter1name=parameter1value", "--parameter2name=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse6() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        InternalSubcommand subcommand1 = new InternalSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        Parameter<Integer> parameter2 = commandLine.createIntegerParameter("parameter2name", "parameter2description");
        subcommand1.add(parameter2);
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1name", "--=parameter1value", "--parameter2name=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse7() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        InternalSubcommand subcommand1 = new InternalSubcommand("subcommand1name", "subcommand1description");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        subcommand1.add(parameter1);
        Parameter<Integer> parameter2 = commandLine.createIntegerParameter("parameter2name", "parameter2description");
        subcommand1.add(parameter2);
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1name", "--parameter1name string", "--parameter2name=6");
    }

    @Test
    public void testParse8() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        commandLine.getCommand().add(parameter1);
        commandLine.getCommand().add(commandLine.createIntegerParameter("parameter2name", "parameter2description"));
        commandLine.getCommand().add(commandLine.createBooleanParameter("parameter3name", "parameter3description"));

        ParsedCommand command =
                commandLine.parse("--parameter1name=parameter1value", "--parameter2name=6", "--parameter3name=true");

        assertEquals(command.getName(), "commandname");
        assertNull(command.getSubcommand());

        assertEquals(command.getStringParameter("parameter1name").getValue(), "parameter1value");
        assertEquals(command.getIntegerParameter("parameter2name").getValue().intValue(), 6);
        assertTrue(command.getBooleanParameter("parameter3name").getValue());
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse9() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        commandLine.getCommand().add(parameter1);
        commandLine.getCommand().add(commandLine.createIntegerParameter("parameter2name", "parameter2description"));

        commandLine.parse("subcommand1name", "--parameter1name=parameter1value", "--parameter2name=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse10() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        Parameter<String> parameter1 = commandLine.createStringParameter("parameter1name", "parameter1description");
        parameter1.setDefaultValue("dummy");
        commandLine.getCommand().add(parameter1);
        commandLine.getCommand().add(commandLine.createIntegerParameter("parameter2name", "parameter2description"));

        commandLine.parse("--parameter1name=parameter1value");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse11() throws ParameterException {
        CommandLine commandLine = new CommandLine("commandname", "commanddescription");
        Subcommand subcommand1 = commandLine.createSubcommand("subcommand1name", "subcommand1description");
        commandLine.getCommand().add(subcommand1);

        commandLine.parse();
    }

    @Test
    public void testPrintHelp() {
        CommandLine commandLine = new CommandLine("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        Subcommand subcommand1 = commandLine.createSubcommand("subcommand1",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        Parameter<String> parameter3 = commandLine.createStringParameter("parameter3",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        parameter3.setDefaultValue("text");
        subcommand1.add(parameter3);
        Parameter<Boolean> parameter4 = commandLine.createBooleanParameter("param4",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        subcommand1.add(parameter4);
        commandLine.getCommand().add(subcommand1);
        Subcommand subcommand2 = commandLine.createSubcommand("subcommand2",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        Parameter<Integer> parameter5 = commandLine.createIntegerParameter("parameter5",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        parameter5.setDefaultValue(5);
        subcommand2.add(parameter5);
        subcommand2.add(new StringParameter("param6",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet."));
        commandLine.getCommand().add(subcommand2);

        List<String> reference = new LinkedList<>();
        reference.add("Usage: commandname <subcommand> [<args>]");
        reference.add("");
        reference.add("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod");
        reference.add("tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At");
        reference.add("vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren,");
        reference.add("no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit");
        reference.add("amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut");
        reference.add("labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam");
        reference.add("et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata");
        reference.add("sanctus est Lorem ipsum dolor sit amet.");
        reference.add("");
        reference.add("Subcommands:");
        reference.add("  subcommand1  Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam");
        reference.add("               nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam");
        reference.add("               erat, sed diam voluptua. At vero eos et accusam et justo duo");
        reference.add("               dolores et ea rebum. Stet clita kasd gubergren, no sea takimata");
        reference.add("               sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit");
        reference.add("               amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor");
        reference.add("               invidunt ut labore et dolore magna aliquyam erat, sed diam");
        reference.add("               voluptua. At vero eos et accusam et justo duo dolores et ea");
        reference.add("               rebum. Stet clita kasd gubergren, no sea takimata sanctus est");
        reference.add("               Lorem ipsum dolor sit amet.");
        reference.add("  subcommand2  Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam");
        reference.add("               nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam");
        reference.add("               erat, sed diam voluptua. At vero eos et accusam et justo duo");
        reference.add("               dolores et ea rebum. Stet clita kasd gubergren, no sea takimata");
        reference.add("               sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit");
        reference.add("               amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor");
        reference.add("               invidunt ut labore et dolore magna aliquyam erat, sed diam");
        reference.add("               voluptua. At vero eos et accusam et justo duo dolores et ea");
        reference.add("               rebum. Stet clita kasd gubergren, no sea takimata sanctus est");
        reference.add("               Lorem ipsum dolor sit amet.");

        assertEquals(commandLine.printHelp().lines().collect(Collectors.toList()), reference);
    }

}
