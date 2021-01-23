/*
 * This file is part of ProDisFuzz, modified on 23.01.21, 20:39.
 * Copyright (c) 2013-2021 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.help;

import internal.commands.InternalCommand;
import internal.commands.InternalSubcommand;
import internal.parameters.BooleanParameter;
import internal.parameters.IntegerParameter;
import internal.parameters.StringParameter;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class MenuTest {

    @Test
    public void testPrintUsage() {
        InternalCommand internalCommand = new InternalCommand("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        internalCommand.add(new BooleanParameter("parameter1",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.").makeOptional(true));
        internalCommand.add(new IntegerParameter("param2",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet."));
        Menu helpMenu = new Menu();

        List<String> reference = new LinkedList<>();
        reference.add("Error: texterror");
        reference.add("");
        reference.add("Usage: commandname --param2 <value> [--parameter1 <value>]");
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
        reference.add("  --parameter1  (Optional) Lorem ipsum dolor sit amet, consetetur sadipscing");
        reference.add("                elitr, sed diam nonumy eirmod tempor invidunt ut labore et");
        reference.add("                dolore magna aliquyam erat, sed diam voluptua. At vero eos et");
        reference.add("                accusam et justo duo dolores et ea rebum. Stet clita kasd");
        reference.add("                gubergren, no sea takimata sanctus est Lorem ipsum dolor sit");
        reference.add("                amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr,");
        reference.add("                sed diam nonumy eirmod tempor invidunt ut labore et dolore magna");
        reference.add("                aliquyam erat, sed diam voluptua. At vero eos et accusam et");
        reference.add("                justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea");
        reference.add("                takimata sanctus est Lorem ipsum dolor sit amet. The default");
        reference.add("                value is 'true'.");

        assertEquals(helpMenu.printUsage(internalCommand, "texterror").lines().collect(Collectors.toList()), reference);
    }

    @Test
    public void testPrintUsage1() {
        InternalCommand internalCommand = new InternalCommand("commandname", "dummy");
        InternalSubcommand internalSubcommand1 = new InternalSubcommand("subcommand1",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.1");
        internalSubcommand1.add(new StringParameter("parameter3",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.3").makeOptional("text"));
        internalSubcommand1.add(new BooleanParameter("param4",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.2"));
        internalCommand.add(internalSubcommand1);
        InternalSubcommand internalSubcommand2 = new InternalSubcommand("subcommand2", "dummy");
        internalSubcommand2.add(new IntegerParameter("parameter5", "dummy").makeOptional(5));
        internalSubcommand2.add(new StringParameter("param6", "dummy"));
        internalCommand.add(internalSubcommand2);
        Menu helpMenu = new Menu();

        List<String> reference = new LinkedList<>();
        reference.add("Error: texterror");
        reference.add("");
        reference.add("Usage: commandname subcommand1 --param4 <value> [--parameter3 <value>]");
        reference.add("");
        reference.add("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod");
        reference.add("tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At");
        reference.add("vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren,");
        reference.add("no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit");
        reference.add("amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut");
        reference.add("labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam");
        reference.add("et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata");
        reference.add("sanctus est Lorem ipsum dolor sit amet.1");
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
        reference.add("                est Lorem ipsum dolor sit amet.2");
        reference.add("  --parameter3  (Optional) Lorem ipsum dolor sit amet, consetetur sadipscing");
        reference.add("                elitr, sed diam nonumy eirmod tempor invidunt ut labore et");
        reference.add("                dolore magna aliquyam erat, sed diam voluptua. At vero eos et");
        reference.add("                accusam et justo duo dolores et ea rebum. Stet clita kasd");
        reference.add("                gubergren, no sea takimata sanctus est Lorem ipsum dolor sit");
        reference.add("                amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr,");
        reference.add("                sed diam nonumy eirmod tempor invidunt ut labore et dolore magna");
        reference.add("                aliquyam erat, sed diam voluptua. At vero eos et accusam et");
        reference.add("                justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea");
        reference.add("                takimata sanctus est Lorem ipsum dolor sit amet.3 The default");
        reference.add("                value is 'text'.");

        assertEquals(helpMenu.printUsage(internalCommand, internalSubcommand1, "texterror").lines()
                .collect(Collectors.toList()), reference);
    }

    @Test
    public void testPrintUsage2() {
        InternalCommand internalCommand = new InternalCommand("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        InternalSubcommand internalSubcommand1 = new InternalSubcommand("subcommand1",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        internalSubcommand1.add(new StringParameter("parameter3",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.").makeOptional("text"));
        internalSubcommand1.add(new BooleanParameter("param4",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet."));
        internalCommand.add(internalSubcommand1);
        InternalSubcommand internalSubcommand2 = new InternalSubcommand("subcommand2",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        internalSubcommand2.add(new IntegerParameter("parameter5",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.").makeOptional(5));
        internalSubcommand2.add(new StringParameter("param6",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet."));
        internalCommand.add(internalSubcommand2);
        Menu helpMenu = new Menu();

        List<String> reference = new LinkedList<>();
        reference.add("Error: texterror");
        reference.add("");
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

        assertEquals(helpMenu.printUsage(internalCommand, "texterror").lines().collect(Collectors.toList()), reference);
    }

    @Test
    public void testPrintUsage3() {
        InternalCommand internalCommand = new InternalCommand("commandname",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                        "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo" +
                        " dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                        "dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
                        "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero " +
                        "eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
                        "sanctus est Lorem ipsum dolor sit amet.");
        Menu helpMenu = new Menu();

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

        assertEquals(helpMenu.printUsage(internalCommand, "texterror").lines().collect(Collectors.toList()), reference);
    }

}
