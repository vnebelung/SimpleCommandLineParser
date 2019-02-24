/*
 * This file is part of ProDisFuzz, modified on 2/21/19 10:32 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import commands.Command;
import commands.Subcommand;
import parameters.InternalParameter;
import parameters.Parameter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class represents the command line parser, which handles the command, its subcommands and parameters used in a
 * command line call. The format of this string is as follows: COMMAND SUBCOMMAND --key1 value1 --key2 value2 ...
 */
@SuppressWarnings("WeakerAccess")
public class CommandLine {

    private final static Pattern PARAMETER_FORMAT = Pattern.compile("--.+=.+");
    private Command command;
    private HelpMenu helpMenu;

    /**
     * Creates a command with a given name and description.
     *
     * @param name        the parameter's name
     * @param description the parameter's description
     */
    public void createCommand(String name, String description) {
        command = new Command(name, description);
        helpMenu = new HelpMenu(command);
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
     * Prints formatted usage information into the output string. This only works if the command is already specified.
     *
     * @return the usage help
     */
    public String printHelp() {
        return helpMenu.printUsage();
    }

    /**
     * Parses a given command line. If no errors occur during the parsing, the parsed arguments will be accessible under
     * the returned command. Otherwise an exception is thrown.
     *
     * @param args the user-provided arguments
     * @return the parsed parameters and subcommands
     * @throws ParameterException if the command line arguments could not be parsed successfully, The exception's
     *                            message contains the formatted error and usage guidance according to the error's
     *                            nature.
     */
    public Command parse(String... args) throws ParameterException {
        Command result = command.copy();
        List<String> arguments = new LinkedList<>(Arrays.asList(args));

        // If a subcommand must be present, check for its existence
        if (command.getSubcommands().size() > 0) {

            // Parse the subcommand
            try {
                parseSubcommand(arguments, result);
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(e.getMessage()));
            }

            Subcommand subcommand = result.getSubcommands().iterator().next();

            try {
                // Parse all parameters of the subcommand
                parseParameters(arguments, subcommand);
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(subcommand.getName(), e.getMessage()));
            }

            // Check if all parameters have values not null. A null value means that a mandatory parameter was not
            // being set via the command line parameters
            for (Parameter parameter : subcommand.getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    throw new ParameterException(helpMenu.printUsage(subcommand.getName(), error));
                }
            }

        } else {

            try {
                // Parse all parameters of the command
                parseParameters(arguments, result);
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(e.getMessage()));
            }

            // Check if all parameters have values not null. A null value means that a mandatory parameter was not
            // being set via the command line parameters
            for (Parameter parameter : result.getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    throw new ParameterException(helpMenu.printUsage(error));
                }
            }

        }

        return result;
    }

    /**
     * Parses the parameters of the command line arguments. A parameter must have a "--key=value" structure. If either
     * the argument format is invalid, an argument contains an unknown parameter name, or an argument's value cannot be
     * parsed to the predefined parameter type, an exception is thrown. The parameters will be added to the given parent
     * subcommand.
     *
     * @param arguments the user-provided command line arguments
     * @param parent    the subcommand to which the parsed parameters will be added
     * @throws ParameterException if an arguments contain an unknown parameter name, an argument format is invalid, or a
     *                            argument's value cannot be parsed into the parameter's type
     */
    private void parseParameters(List<String> arguments, Subcommand parent) throws ParameterException {
        for (String argument : arguments) {
            if (!PARAMETER_FORMAT.matcher(argument).matches()) {
                throw new ParameterException("Parameter '" + argument + "' has no valid --key=value structure");
            }
            String key = argument.substring(2, argument.indexOf('='));
            String value = argument.substring(argument.indexOf('=') + 1);
            InternalParameter parameter =
                    parent.getParameters().stream().filter(p -> p.getName().equals(key)).findFirst()
                            .orElseThrow(() -> new ParameterException("Unknown parameter '" + key + "'"));
            parameter.setValue(value);
        }
    }

    /**
     * Parses the subcommand of the command line arguments. The subcommand must be at index 0 of the given arguments. If
     * either the given arguments are empty or the first argument does not contain any of the predefined given
     * subcommands, an exception is thrown. The subcommand will be added to the given parent command.
     *
     * @param arguments the user-provided command line arguments
     * @param parent    the command to which the parsed subcommand will be added
     * @throws ParameterException if the given arguments do not contain a subcommand at the first index
     */
    private void parseSubcommand(List<String> arguments, Command parent) throws ParameterException {
        if (arguments.isEmpty()) {
            throw new ParameterException("No subcommand found");
        }
        if (!command.getSubcommands().stream().map(Subcommand::getName).collect(Collectors.toSet())
                .contains(arguments.get(0))) {
            throw new ParameterException("Unknown subcommand '" + arguments.get(0) + "'");
        }
        parent.add(command.getSubcommand(arguments.remove(0)).copy());
    }

}
