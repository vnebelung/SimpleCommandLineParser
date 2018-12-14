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
import parameters.Parameter;

import java.util.*;

/**
 * This class is the help menu printer that is responsible for printing information about the correct usage of command
 * line options to the terminal.
 */
public class HelpMenu {

    @SuppressWarnings("FieldCanBeLocal")
    private static int TERMINAL_WIDTH = 80;

    /**
     * Prints a given error message to the standard output stream.
     *
     * @param message the error message
     */
    private static void printError(String message) {
        System.out.printf("Error: %s%n", message);
    }

    /**
     * Prints a given error message for a given command to the standard output stream. Additionally a usage block is
     * printed that lists all of the command's possible parameters and subcommands.
     *
     * @param command the command
     * @param error   the error message
     */
    public static void print(Command command, String error) {
        printError(error);

        List<String> strings = new LinkedList<>();
        strings.add(command.getName());
        command.getParameters().stream().filter(Parameter::isMandatory).map(p -> "--" + p.getName() + "=<value>")
                .forEach(strings::add);
        command.getParameters().stream().filter(p -> !p.isMandatory()).map(p -> "[--" + p.getName() + "=<value>]")
                .forEach(strings::add);
        if (!command.getSubcommands().isEmpty()) {
            strings.add("<subcommand>");
            strings.add("[<args>]");
        }
        printUsage(strings);

        System.out.println();
        printIntendedBlock(Arrays.asList(command.getDescription().split(" ")), 0);

        if (!command.getParameters().isEmpty()) {
            System.out.println();
            printParameters(command.getParameters());
        }
        if (!command.getSubcommands().isEmpty()) {
            System.out.println();
            printSubcommands(command.getSubcommands());
        }
    }

    /**
     * Prints a given error message for a given subcommand to the standard output stream. Additionally a usage block is
     * printed that lists all of the subcommand's possible parameters.
     *
     * @param command the command
     * @param error   the error message
     */
    public static void print(Command command, String subcommandName, String error) {
        printError(error);

        List<String> strings = new LinkedList<>();
        strings.add(command.getName());
        strings.add(subcommandName);
        command.getSubcommand(subcommandName).getParameters().stream().filter(Parameter::isMandatory)
                .map(p -> "--" + p.getName() + "=<value>").forEach(strings::add);
        command.getSubcommand(subcommandName).getParameters().stream().filter(p -> !p.isMandatory())
                .map(p -> "[--" + p.getName() + "=<value>]").forEach(strings::add);

        printUsage(strings);

        System.out.println();
        printIntendedBlock(Arrays.asList(command.getSubcommand(subcommandName).getDescription().split(" ")), 0);

        System.out.println();
        printParameters(command.getSubcommand(subcommandName).getParameters());
    }

    /**
     * Prints the usage block of given "command subcommand parameter" strings with indented lines (except for the first)
     * to the standard output stream.
     *
     * @param strings the "command subcommand parameter" strings
     */
    private static void printUsage(List<String> strings) {
        System.out.println();

        List<String> parts = new LinkedList<>();
        parts.add("Usage:");
        parts.addAll(strings);

        printIntendedBlock(parts, 10);

    }

    /**
     * Prints an block of given strings to the standard output stream with the given indent.  The first line is not
     * indented. A line has maximum TERMINAL_WIDTH characters.
     *
     * @param indent the indent in spaces
     * @param parts  the string parts that are printed indented
     */
    private static void printIntendedBlock(List<String> parts, int indent) {
        String spaces = indent == 0 ? "" : String.format("%" + indent + "s", "");
        StringBuilder line = new StringBuilder();
        for (String part : parts) {
            if (line.length() + 1 + part.length() > TERMINAL_WIDTH) {
                System.out.println(line.toString());
                line.setLength(0);
                line.append(spaces);
            }
            if (line.length() > 0 && line.charAt(line.length() - 1) != ' ') {
                line.append(" ");
            }
            line.append(part);
        }
        System.out.println(line.toString());
    }

    /**
     * Prints the parameter block with a list of all possible parameters and their descriptions. The description of the
     * parameters is indented.
     *
     * @param parameters the parameters
     */
    private static void printParameters(Set<Parameter> parameters) {
        System.out.println("Options:");

        //noinspection OptionalGetWithoutIsPresent
        int maxNameWidth = parameters.stream().map(Parameter::getName).map(String::length)
                .max(Comparator.comparing(Integer::intValue)).get();
        for (Parameter parameter : parameters) {
            List<String> parts = new LinkedList<>();
            parts.add(String.format("  --%1$-" + maxNameWidth + "s  ", parameter.getName()));
            parts.addAll(Arrays.asList(parameter.getDescription().split(" ")));
            printIntendedBlock(parts, maxNameWidth + 6);
        }
    }

    /**
     * Prints the subcommand block with a list of all possible subcommands and their descriptions. The description of
     * the subcommands is indented.
     *
     * @param subcommands the subcommands
     */
    private static void printSubcommands(Set<Subcommand> subcommands) {
        System.out.println("Subcommands:");

        //noinspection OptionalGetWithoutIsPresent
        int maxNameWidth = subcommands.stream().map(Subcommand::getName).map(String::length)
                .max(Comparator.comparing(Integer::intValue)).get();
        for (Subcommand subcommand : subcommands) {
            List<String> parts = new LinkedList<>();
            parts.add(String.format("  %1$-" + maxNameWidth + "s  ", subcommand.getName()));
            parts.addAll(Arrays.asList(subcommand.getDescription().split(" ")));
            printIntendedBlock(parts, maxNameWidth + 6);
        }
    }

}
