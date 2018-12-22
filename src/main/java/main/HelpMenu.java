/*
 * This file is part of ProDisFuzz, modified on 12/16/18 3:46 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import commands.Command;
import commands.Subcommand;
import parameters.Parameter;

import java.util.*;

/**
 * This class is the help menu printer that is responsible for printing information about the correct usage of command
 * line options to the terminal.
 */
class HelpMenu {

    @SuppressWarnings("FieldCanBeLocal")
    private static int TERMINAL_WIDTH = 80;
    private final Command command;

    /**
     * Instantiates a new help menu that prints out usage help menus for a given command.
     *
     * @param command the command
     */
    HelpMenu(Command command) {
        this.command = command;
    }

    /**
     * Prints a given error message to the returned string.
     *
     * @param message the error message
     * @return the formatted error message
     */
    private String printError(String message) {
        return "Error: " + message + System.lineSeparator();
    }

    /**
     * Prints a given error message for to the returned string. Additionally a usage block is printed that lists all of
     * the command's possible parameters and subcommands.
     *
     * @param error the error message
     * @return the error message and usage help
     */
    String printUsage(String error) {
        return printError(error) + System.lineSeparator() + printUsage();
    }

    /**
     * Prints a given error message for a given subcommand to the returned string. Additionally a usage block is printed
     * that lists all of the subcommand's possible parameters.
     *
     * @param subcommandName the subcommand's name
     * @param error          the error message
     * @return the error message and usage help
     */
    String printUsage(String subcommandName, String error) {
        StringBuilder result = new StringBuilder();
        result.append(printError(error));

        List<String> strings = new LinkedList<>();
        strings.add(command.getName());
        strings.add(subcommandName);
        command.getSubcommand(subcommandName).getParameters().stream().filter(Parameter::isMandatory)
                .map(p -> "--" + p.getName() + "=<value>").forEach(strings::add);
        command.getSubcommand(subcommandName).getParameters().stream().filter(p -> !p.isMandatory())
                .map(p -> "[--" + p.getName() + "=<value>]").forEach(strings::add);

        result.append(System.lineSeparator());
        result.append(printUsage(strings));

        result.append(System.lineSeparator());
        result.append(
                printIntendedBlock(Arrays.asList(command.getSubcommand(subcommandName).getDescription().split(" ")),
                        0));

        result.append(System.lineSeparator());
        result.append(printParameters(command.getSubcommand(subcommandName).getParameters()));

        return result.toString();
    }

    /**
     * Prints a usage block to the returned string that lists all of the command's possible parameters and subcommands.
     *
     * @return the error message and usage help
     */
    String printUsage() {
        StringBuilder result = new StringBuilder();

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
        result.append(printUsage(strings));

        result.append(System.lineSeparator());
        result.append(printIntendedBlock(Arrays.asList(command.getDescription().split(" ")), 0));

        if (!command.getParameters().isEmpty()) {
            result.append(System.lineSeparator());
            result.append(printParameters(command.getParameters()));
        }
        if (!command.getSubcommands().isEmpty()) {
            result.append(System.lineSeparator());
            result.append(printSubcommands(command.getSubcommands()));
        }

        return result.toString();
    }

    /**
     * Prints the usage block of given "command subcommand parameter" strings with indented lines (except for the first)
     * to the returned string.
     *
     * @param strings the "command subcommand parameter" strings
     * @return the usage help
     */
    private String printUsage(List<String> strings) {
        StringBuilder result = new StringBuilder();

        List<String> parts = new LinkedList<>();
        parts.add("Usage:");
        parts.addAll(strings);

        result.append(printIntendedBlock(parts, 10));

        return result.toString();
    }

    /**
     * Prints an block of given strings to the returned string with the given indent.  The first line is not indented. A
     * line has maximum TERMINAL_WIDTH characters.
     *
     * @param indent the indent in spaces
     * @param parts  the string parts that are printed indented
     * @return the intended block
     */
    private String printIntendedBlock(List<String> parts, int indent) {
        StringBuilder result = new StringBuilder();

        String spaces = indent == 0 ? "" : String.format("%" + indent + "s", "");
        StringBuilder line = new StringBuilder();
        for (String part : parts) {
            if (line.length() + 1 + part.length() > TERMINAL_WIDTH) {
                result.append(line).append(System.lineSeparator());
                line.setLength(0);
                line.append(spaces);
            }
            if (line.length() > 0 && line.charAt(line.length() - 1) != ' ') {
                line.append(" ");
            }
            line.append(part);
        }
        result.append(line).append(System.lineSeparator());

        return result.toString();
    }

    /**
     * Prints the parameter block with a list of all possible parameters and their descriptions. The description of the
     * parameters is indented.
     *
     * @param parameters the parameters
     * @return the parameter block
     */
    private String printParameters(Set<Parameter> parameters) {
        StringBuilder result = new StringBuilder();
        result.append("Options:").append(System.lineSeparator());

        //noinspection OptionalGetWithoutIsPresent
        int maxNameWidth = parameters.stream().map(Parameter::getName).map(String::length)
                .max(Comparator.comparing(Integer::intValue)).get();
        for (Parameter parameter : parameters) {
            List<String> parts = new LinkedList<>();
            parts.add(String.format("  --%1$-" + maxNameWidth + "s  ", parameter.getName()));
            parts.addAll(Arrays.asList(parameter.getDescription().split(" ")));
            result.append(printIntendedBlock(parts, maxNameWidth + 6));
        }

        return result.toString();
    }

    /**
     * Prints the subcommand block with a list of all possible subcommands and their descriptions. The description of
     * the subcommands is indented.
     *
     * @param subcommands the subcommands
     * @return the subcommand block
     */
    private String printSubcommands(Set<Subcommand> subcommands) {
        StringBuilder result = new StringBuilder();
        result.append("Subcommands:").append(System.lineSeparator());

        //noinspection OptionalGetWithoutIsPresent
        int maxNameWidth = subcommands.stream().map(Subcommand::getName).map(String::length)
                .max(Comparator.comparing(Integer::intValue)).get();
        for (Subcommand subcommand : subcommands) {
            List<String> parts = new LinkedList<>();
            parts.add(String.format("  %1$-" + maxNameWidth + "s  ", subcommand.getName()));
            parts.addAll(Arrays.asList(subcommand.getDescription().split(" ")));
            result.append(printIntendedBlock(parts, maxNameWidth + 4));
        }

        return result.toString();
    }

}