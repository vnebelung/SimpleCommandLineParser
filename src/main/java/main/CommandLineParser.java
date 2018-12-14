/*
 * This file is part of ProDisFuzz, modified on 12/14/18 6:19 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import commands.Command;
import commands.Subcommand;
import menu.HelpMenu;
import parameters.Parameter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class represents the command line parser, which handles the command, its subcommands and parameters used in a
 * command line call. The format of this string is as follows: COMMAND SUBCOMMAND --key1 value1 --key2 value2 ...
 */
@SuppressWarnings("WeakerAccess")
public class CommandLineParser {

    private final static Pattern PARAMETER_FORMAT = Pattern.compile("--.+=.+");
    private Command command;

    /**
     * Creates a command with a given name and description.
     *
     * @param name        the parameter's name
     * @param description the parameter's description
     */
    public void createCommand(String name, String description) {
        command = new Command(name, description);
    }

    /**
     * Returns the command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Parses a given command line. If no errors occur during the parsing, the parsed arguments will be accessible under
     * the command of this command line parser. Otherwise an exception is thrown.
     *
     * @param args the user-provided arguments
     * @throws ParameterException if the command line arguments could not be parsed successfully
     */
    public void parse(String... args) throws ParameterException {
        List<String> arguments = new LinkedList<>(Arrays.asList(args));

        // If a subcommand must be present, check for its existence
        if (command.getSubcommands().size() > 0) {

            try {
                parseSubcommand(arguments,
                        command.getSubcommands().stream().map(Subcommand::getName).collect(Collectors.toSet()));
            } catch (ParameterException e) {
                HelpMenu.print(command, e.getMessage());
                throw e;
            }
            // Now we know that a valid subcommand is at args[0]
            String subcommandName = arguments.remove(0);

            try {
                // Parse all parameters of the subcommand
                parseParameters(arguments, command.getSubcommand(subcommandName).getParameters());
            } catch (ParameterException e) {
                HelpMenu.print(command, subcommandName, e.getMessage());
                throw e;
            }

            // Check if all parameters have their values set
            for (Parameter parameter : command.getSubcommand(subcommandName).getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    HelpMenu.print(command, subcommandName, error);
                    throw new ParameterException(error);
                }
            }
        } else {
            try {
                // Parse all parameters of the command
                parseParameters(arguments, command.getParameters());
            } catch (ParameterException e) {
                HelpMenu.print(command, e.getMessage());
                throw e;
            }

            // Check if all parameters have their values set
            for (Parameter parameter : command.getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    HelpMenu.print(command, error);
                    throw new ParameterException(error);
                }
            }
        }


    }

    /**
     * Parses the parameters of the command line arguments. A parameter must have a "--key=value" structure. If either
     * the argument format is invalid, an argument contains an unknown parameter name, or an argument's value cannot be
     * parsed to the predefined parameter type, an exception is thrown.
     *
     * @param arguments  the user-provided command line arguments
     * @param parameters the predefined parameters
     * @throws ParameterException if an arguments contain an unknown parameter name, an argument format is invalid, or a
     *                            argument's value cannot be parsed into the parameter's type
     */
    private void parseParameters(List<String> arguments, Set<Parameter> parameters) throws ParameterException {
        for (String argument : arguments) {
            if (!PARAMETER_FORMAT.matcher(argument).matches()) {
                throw new ParameterException("Parameter '" + argument + "' has no valid --key=value structure");
            }
            String key = argument.substring(2, argument.indexOf('='));
            String value = argument.substring(argument.indexOf('=') + 1);
            parameters.stream().filter(p -> p.getName().equals(key)).findFirst()
                    .orElseThrow(() -> new ParameterException("Unknown parameter '" + key + "'")).setValue(value);
        }
    }

    /**
     * Parses the subcommand of the command line arguments. If existent at all, the subcommand must be at index 0 of the
     * given arguments. If either the given arguments are empty or the first argument does not contain any of the
     * predefined given subcommands, an exception is thrown.
     *
     * @param arguments       the user-provided command line arguments
     * @param subcommandNames the predefined subcommand names
     * @throws ParameterException if the arguments do not contain a subcommand at the first index
     */
    private void parseSubcommand(List<String> arguments, Set<String> subcommandNames) throws ParameterException {
        if (arguments.isEmpty()) {
            throw new ParameterException("No subcommand found");
        }
        if (!subcommandNames.contains(arguments.get(0))) {
            throw new ParameterException("Unknown subcommand '" + arguments.get(0) + "'");
        }
    }

}
