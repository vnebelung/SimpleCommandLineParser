/*
 * This file is part of ProDisFuzz, modified on 29.04.25, 07:03.
 * Copyright (c) 2013-2025 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class SimpleCommandLineParserTest {

    @Test
    public void testGetCommand() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        assertNull(commandLine.getCommand());
    }

    @Test
    public void testGetCommand1() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        commandLine.setCommand(CommandFactory.createCommand("parametername", "parameterdescription"));
        assertNotNull(commandLine.getCommand());
    }

    @Test
    public void testParse() throws ParameterException {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        subcommand1.add(ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2"));
        subcommand1.add(ParameterFactory.createBooleanParameter("parametername3", "parameterdescription3"));
        command.add(subcommand1);
        Subcommand subcommand2 = CommandFactory.createSubcommand("subcommandname2", "subcommanddescription2");
        subcommand2.add(ParameterFactory.createStringParameter("parametername4", "parameterdescription4")
                .makeOptional("dummy"));
        subcommand2.add(ParameterFactory.createIntegerParameter("parametername5", "parameterdescription5"));
        subcommand2.add(ParameterFactory.createBooleanParameter("parametername6", "parameterdescription6"));
        command.add(subcommand2);
        commandLine.setCommand(command);

        ParsedCommand parsedCommand = commandLine
                .parse("subcommandname1", "--parametername1", "parametervalue1", "--parametername2", "6",
                        "--parametername3", "true");

        assertEquals(parsedCommand.getName(), "commandname");

        assertNotNull(parsedCommand.getSubcommand());
        assertEquals(parsedCommand.getSubcommand().getName(), "subcommandname1");

        assertEquals(parsedCommand.getSubcommand().getStringParameter("parametername1").getValue(), "parametervalue1");
        assertEquals(parsedCommand.getSubcommand().getIntegerParameter("parametername2").getValue().intValue(), 6);
        assertTrue(parsedCommand.getSubcommand().getBooleanParameter("parametername3").getValue());
    }

    @Test
    public void testParse1() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        Parameter<Boolean> parameter2 =
                ParameterFactory.createBooleanParameter("parametername2", "parameterdescription2");
        subcommand1.add(parameter2);
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class,
                () -> commandLine.parse("subcommandname1", "--parametername1", "parametervalue1"));
    }

    @Test
    public void testParse2() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        Parameter<Integer> parameter2 =
                ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2");
        subcommand1.add(parameter2);
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class,
                () -> commandLine.parse("subcommandname1", "--parametername1", "--parametername2", "2"));
    }

    @Test
    public void testParse3() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        Parameter<Integer> parameter2 =
                ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2");
        subcommand1.add(parameter2);
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class, () -> commandLine
                .parse("--parametername1", "parametervalue1", "--parametername2", "6", "subcommandname1"));
    }

    @Test
    public void testParse4() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        Parameter<Integer> parameter2 =
                ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2");
        subcommand1.add(parameter2);
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class, () -> commandLine
                .parse("subcommandname1", "parametername1", "parametervalue1", "--parametername2", "6"));
    }

    @Test
    public void testParse5() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        Parameter<Integer> parameter2 =
                ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2");
        subcommand1.add(parameter2);
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class, () -> commandLine
                .parse("subcommandname1", "-parametername1", "parametervalue1", "--parametername2", "6"));
    }

    @Test
    public void testParse6() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        Parameter<Integer> parameter2 =
                ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2");
        subcommand1.add(parameter2);
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class,
                () -> commandLine.parse("subcommandname1", "--=parametervalue1", "--parametername2", "6"));
    }

    @Test
    public void testParse7() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        subcommand1.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        Parameter<Integer> parameter2 =
                ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2");
        subcommand1.add(parameter2);
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class,
                () -> commandLine.parse("subcommandname1", "--parametername1 string", "--parametername2", "6"));
    }

    @Test
    public void testParse8() throws ParameterException {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        command.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        command.add(ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2"));
        command.add(ParameterFactory.createBooleanParameter("parametername3", "parameterdescription3"));
        commandLine.setCommand(command);

        ParsedCommand parsedCommand = commandLine
                .parse("--parametername1", "parametervalue1", "--parametername2", "6", "--parametername3", "true");

        assertEquals(parsedCommand.getName(), "commandname");
        assertNull(parsedCommand.getSubcommand());

        assertEquals(parsedCommand.getStringParameter("parametername1").getValue(), "parametervalue1");
        assertEquals(parsedCommand.getIntegerParameter("parametername2").getValue().intValue(), 6);
        assertTrue(parsedCommand.getBooleanParameter("parametername3").getValue());
    }

    @Test
    public void testParse9() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        command.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        command.add(ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2"));
        commandLine.setCommand(command);

        assertThrows(ParameterException.class, () -> commandLine
                .parse("subcommandname1", "--parametername1", "parametervalue1", "--parametername2", "6"));
    }

    @Test
    public void testParse10() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        command.add(ParameterFactory.createStringParameter("parametername1", "parameterdescription1")
                .makeOptional("dummy"));
        command.add(ParameterFactory.createIntegerParameter("parametername2", "parameterdescription2"));
        commandLine.setCommand(command);

        assertThrows(ParameterException.class, () -> commandLine.parse("--parametername1", "parametervalue1"));
    }

    @Test
    public void testParse11() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommandname1", "subcommanddescription1");
        command.add(subcommand1);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class, commandLine::parse);
    }

    @Test
    public void testParse12() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand = CommandFactory.createSubcommand("subcommandname", "subcommanddescription");
        Parameter<String> parameter1 =
                ParameterFactory.createStringParameter("parametername1", "parameterdescription1");
        subcommand.add(parameter1);
        Parameter<String> parameter2 =
                ParameterFactory.createStringParameter("parametername2", "parameterdescription2")
                        .makeOptional("default1");
        subcommand.add(parameter2);
        Parameter<String> parameter3 =
                ParameterFactory.createStringParameter("parametername3", "parameterdescription3");
        subcommand.add(parameter3);
        command.add(subcommand);
        commandLine.setCommand(command);

        List<String> reference = new LinkedList<>();
        reference.add("Error: Parameter 'wrong' has no valid format");
        reference.add("");
        reference.add("Usage: java -jar commandname subcommandname --parametername1 <value>");
        reference.add("          --parametername3 <value> [--parametername2 <value>]");
        reference.add("");
        reference.add("subcommanddescription");
        reference.add("");
        reference.add("Options:");
        reference.add("  --parametername1  parameterdescription1");
        reference.add("  --parametername2  (Optional) parameterdescription2 The default value is");
        reference.add("                    'default1'.");
        reference.add("  --parametername3  parameterdescription3");

        try {
            commandLine.parse("subcommandname", "--parametername1", "parametervalue1", "wrong", "--parametername2",
                    "parametervalue2", "--parametername3", "parametervalue3");
            fail();
        } catch (ParameterException e) {
            assertEquals(e.getMessage().lines().collect(Collectors.toList()), reference);
        }
    }

    @Test
    public void testParse13() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname", "commanddescription");
        Subcommand subcommand = CommandFactory.createSubcommand("subcommandname", "subcommanddescription");
        command.add(subcommand);
        commandLine.setCommand(command);

        assertThrows(ParameterException.class, commandLine::parse);
    }

    @Test
    public void testPrintHelp() {
        SimpleCommandLineParser commandLine = new SimpleCommandLineParser();
        Command command = CommandFactory.createCommand("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        Subcommand subcommand1 = CommandFactory.createSubcommand("subcommand1",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        subcommand1.add(ParameterFactory.createStringParameter("parameter3",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.").makeOptional("text"));
        Parameter<Boolean> parameter4 = ParameterFactory.createBooleanParameter("param4",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        subcommand1.add(parameter4);
        command.add(subcommand1);
        Subcommand subcommand2 = CommandFactory.createSubcommand("subcommand2",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        subcommand2.add(ParameterFactory.createIntegerParameter("parameter5",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.").makeOptional(5));
        subcommand2.add(ParameterFactory.createStringParameter("param6",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet."));
        command.add(subcommand2);
        commandLine.setCommand(command);

        List<String> reference = new LinkedList<>();
        reference.add("Usage: java -jar commandname <subcommand> [<args>]");
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
