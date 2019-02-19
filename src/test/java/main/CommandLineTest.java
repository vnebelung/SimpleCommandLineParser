/*
 * This file is part of ProDisFuzz, modified on 2/19/19 10:35 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import commands.Command;
import commands.Subcommand;
import org.testng.annotations.Test;
import parameters.BooleanParameter;
import parameters.IntegerParameter;
import parameters.StringParameter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class CommandLineTest {

    @Test
    public void testCreateCommand() {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("testname", "testdescription");
        assertEquals(commandLine.getCommand().getName(), "testname");
        assertEquals(commandLine.getCommand().getDescription(), "testdescription");
    }

    @Test
    public void testGetCommand() {
        CommandLine commandLine = new CommandLine();
        assertNull(commandLine.getCommand());

    }

    @Test
    public void testGetCommand1() {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("testname", "testdescriptipn");
        assertNotNull(commandLine.getCommand());
    }

    @Test
    public void testParse() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        Command command = commandLine.parse("subcommand1", "--parameter1=string", "--parameter2=6");
        assertEquals(command.getName(), "command");
        assertEquals(command.getSubcommands().size(), 1);
        assertEquals(command.getSubcommands().iterator().next().getName(), "subcommand1");
        assertEquals(command.getSubcommand("subcommand1").getParameters().size(), 2);
        assertEquals(command.getSubcommand("subcommand1").getStringParameter("parameter1").getValue(), "string");
        assertEquals(command.getSubcommand("subcommand1").getIntegerParameter("parameter2").getValue().intValue(), 6);
    }

    @Test
    public void testParse1() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new BooleanParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        Command command = commandLine.parse("subcommand1", "--parameter2=true");
        assertEquals(command.getName(), "command");
        assertEquals(command.getSubcommands().size(), 1);
        assertEquals(command.getSubcommands().iterator().next().getName(), "subcommand1");
        assertEquals(command.getSubcommand("subcommand1").getParameters().size(), 2);
        assertEquals(command.getSubcommand("subcommand1").getStringParameter("parameter1").getValue(), "dummy");
        assertTrue(command.getSubcommand("subcommand1").getBooleanParameter("parameter2").getValue());
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse2() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new BooleanParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1", "--parameter1=dummy");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse3() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1", "--parameter1=", "--parameter4=2");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse4() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter3", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter4", "description"));
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("--parameter3=value", "--parameter4=6", "subcommand1");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse5() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1", "parameter1=string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse6() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1", "-parameter1=string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse7() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1", "--=string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse8() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        subcommand1.add(new StringParameter("parameter1", "description", "dummy"));
        subcommand1.add(new IntegerParameter("parameter2", "description"));
        commandLine.getCommand().add(subcommand1);

        commandLine.parse("subcommand1", "--parameter1 string", "--parameter2=6");
    }

    @Test
    public void testParse9() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        commandLine.getCommand().add(new StringParameter("parameter1", "description", "dummy"));
        commandLine.getCommand().add(new IntegerParameter("parameter2", "description"));

        commandLine.parse("--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse10() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        commandLine.getCommand().add(new StringParameter("parameter1", "description", "dummy"));
        commandLine.getCommand().add(new IntegerParameter("parameter2", "description"));

        commandLine.parse("subcommand1", "--parameter1 string", "--parameter2=6");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse11() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        commandLine.getCommand().add(new StringParameter("parameter1", "description", "dummy"));
        commandLine.getCommand().add(new IntegerParameter("parameter2", "description"));

        commandLine.parse("--parameter1=dummy");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testParse12() throws ParameterException {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("command", "description");
        Subcommand subcommand1 = new Subcommand("subcommand1", "description");
        commandLine.getCommand().add(subcommand1);

        commandLine.parse();
    }

    @Test
    public void testPrintHelp() {
        CommandLine commandLine = new CommandLine();
        commandLine.createCommand("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        Subcommand subcommand1 = new Subcommand("subcommand1",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        subcommand1.add(new StringParameter("parameter3",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.", "text"));
        subcommand1.add(new BooleanParameter("param4",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet."));
        commandLine.getCommand().add(subcommand1);
        Subcommand subcommand2 = new Subcommand("subcommand2",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        subcommand2.add(new IntegerParameter("parameter5",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.", 5));
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

    @Test
    public void testParse13() {
    }
}
