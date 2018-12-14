/*
 * This file is part of ProDisFuzz, modified on 12/14/18 6:19 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package menu;

import commands.Command;
import commands.Subcommand;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import parameters.BooleanParameter;
import parameters.IntegerParameter;
import parameters.StringParameter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class HelpMenuTest {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private PrintStream old = System.out;

    @BeforeMethod
    public void setUp() {
        byteArrayOutputStream.reset();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
    }

    @AfterMethod
    public void tearDown() {
        System.setOut(old);
    }

    @Test
    public void testPrint() {
        Command command = new Command("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        command.add(new BooleanParameter("parameter1",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.", true));
        command.add(new IntegerParameter("param2",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet."));

        HelpMenu.print(command, "texterror");
        List<String> actual =
                byteArrayOutputStream.toString(StandardCharsets.UTF_8).lines().collect(Collectors.toList());

        List<String> reference = new LinkedList<>();
        reference.add("Error: texterror");
        reference.add("");
        reference.add("Usage: commandname --param2=<value> [--parameter1=<value>]");
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
        reference.add("Options:");
        reference.add("  --param2      Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed");
        reference.add("                diam nonumy eirmod tempor invidunt ut labore et dolore magna");
        reference.add("                aliquyam erat, sed diam voluptua. At vero eos et accusam et");
        reference.add("                justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea");
        reference.add("                takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum");
        reference.add("                dolor sit amet, consetetur sadipscing elitr, sed diam nonumy");
        reference.add("                eirmod tempor invidunt ut labore et dolore magna aliquyam erat,");
        reference.add("                sed diam voluptua. At vero eos et accusam et justo duo dolores");
        reference.add("                et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus");
        reference.add("                est Lorem ipsum dolor sit amet.");
        reference.add("  --parameter1  Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed");
        reference.add("                diam nonumy eirmod tempor invidunt ut labore et dolore magna");
        reference.add("                aliquyam erat, sed diam voluptua. At vero eos et accusam et");
        reference.add("                justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea");
        reference.add("                takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum");
        reference.add("                dolor sit amet, consetetur sadipscing elitr, sed diam nonumy");
        reference.add("                eirmod tempor invidunt ut labore et dolore magna aliquyam erat,");
        reference.add("                sed diam voluptua. At vero eos et accusam et justo duo dolores");
        reference.add("                et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus");
        reference.add("                est Lorem ipsum dolor sit amet.");

        assertEquals(actual, reference);
    }

    @Test
    public void testPrint1() {
        Command command = new Command("commandname",
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
        command.add(subcommand1);
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
        command.add(subcommand2);

        HelpMenu.print(command, "subcommand1", "texterror");
        List<String> actual =
                byteArrayOutputStream.toString(StandardCharsets.UTF_8).lines().collect(Collectors.toList());

        List<String> reference = new LinkedList<>();
        reference.add("Error: texterror");
        reference.add("");
        reference.add("Usage: commandname subcommand1 --param4=<value> [--parameter3=<value>]");
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
        reference.add("Options:");
        reference.add("  --param4      Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed");
        reference.add("                diam nonumy eirmod tempor invidunt ut labore et dolore magna");
        reference.add("                aliquyam erat, sed diam voluptua. At vero eos et accusam et");
        reference.add("                justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea");
        reference.add("                takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum");
        reference.add("                dolor sit amet, consetetur sadipscing elitr, sed diam nonumy");
        reference.add("                eirmod tempor invidunt ut labore et dolore magna aliquyam erat,");
        reference.add("                sed diam voluptua. At vero eos et accusam et justo duo dolores");
        reference.add("                et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus");
        reference.add("                est Lorem ipsum dolor sit amet.");
        reference.add("  --parameter3  Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed");
        reference.add("                diam nonumy eirmod tempor invidunt ut labore et dolore magna");
        reference.add("                aliquyam erat, sed diam voluptua. At vero eos et accusam et");
        reference.add("                justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea");
        reference.add("                takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum");
        reference.add("                dolor sit amet, consetetur sadipscing elitr, sed diam nonumy");
        reference.add("                eirmod tempor invidunt ut labore et dolore magna aliquyam erat,");
        reference.add("                sed diam voluptua. At vero eos et accusam et justo duo dolores");
        reference.add("                et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus");
        reference.add("                est Lorem ipsum dolor sit amet.");

        assertEquals(actual, reference);
    }

    @Test
    public void testPrint2() {
        Command command = new Command("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");

        HelpMenu.print(command, "texterror");
        List<String> actual =
                byteArrayOutputStream.toString(StandardCharsets.UTF_8).lines().collect(Collectors.toList());

        List<String> reference = new LinkedList<>();
        reference.add("Error: texterror");
        reference.add("");
        reference.add("Usage: commandname");
        reference.add("");
        reference.add("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod");
        reference.add("tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At");
        reference.add("vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren,");
        reference.add("no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit");
        reference.add("amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut");
        reference.add("labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam");
        reference.add("et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata");
        reference.add("sanctus est Lorem ipsum dolor sit amet.");

        assertEquals(actual, reference);
    }

}
