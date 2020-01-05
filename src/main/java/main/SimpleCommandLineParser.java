/*
 * This file is part of ProDisFuzz, modified on 05.01.20, 10:25.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import internal.commands.InternalCommand;
import internal.commands.InternalSubcommand;
import internal.help.HelpMenu;
import internal.parameters.AbstractParameter;
import internal.parameters.BooleanParameter;
import internal.parameters.IntegerParameter;
import internal.parameters.StringParameter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class represents the command line parser, which handles the command, its subcommands and parameters used in a
 * command line call. The format of this string is as follows: COMMAND SUBCOMMAND --key1 value1 --key2 value2…
 */
@SuppressWarnings("WeakerAccess")
public class SimpleCommandLineParser {

    private final static Pattern PARAMETER_FORMAT = Pattern.compile("--.+=.+");
    private InternalCommand command;
    private HelpMenu helpMenu;

    /**
     * Creates a command line parser with a command with a given name and description. Usually the command is the name
     * of the executable file (or jar file) that the user will call on the command line. Since this file name does not
     * change and is always the same for every call, you can only define one single command.
     *
     * @param commandName        the command's name
     * @param commandDescription the command's description for the help menu
     */
    public SimpleCommandLineParser(String commandName, String commandDescription) {
        command = new InternalCommand(commandName, commandDescription);
        helpMenu = new HelpMenu(command);
    }

    /**
     * Creates a subcommand with a given name and description. The subcommand is used to distinguish different
     * operations that a user can choose on the command line. Every subcommand has its independent set of parameters.
     *
     * @param name        the subcommand's name
     * @param description the subcommand's description for the help menu
     * @return the created subcommand
     */
    public Subcommand createSubcommand(String name, String description) {
        return new InternalSubcommand(name, description);
    }

    /**
     * Creates a boolean parameter with a given name and description. The parameter must be attached to a command or a
     * subcommand and is used to define a key value pair that a user can use to submit a boolean input. The key is
     * defined by the given name. The parameter is by default mandatory. If the parameter shall be optional, you must
     * set its default value.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @return the created parameter
     */
    public Parameter<Boolean> createBooleanParameter(String name, String description) {
        return new BooleanParameter(name, description);
    }

    /**
     * Creates an integer parameter with a given name and description. The parameter must be attached to a command or a
     * subcommand and is used to define a key value pair that a user can use to submit an integer input. The key is
     * defined by the given name. The parameter is by default mandatory. If the parameter shall be optional, you must
     * set its default value.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @return the created parameter
     */
    public Parameter<Integer> createIntegerParameter(String name, String description) {
        return new IntegerParameter(name, description);
    }

    /**
     * Creates a string parameter with a given name and description. The parameter must be attached to a command or a
     * subcommand and is used to define a key value pair that a user can use to submit a string input. The key is
     * defined by the given name. The parameter is by default mandatory. If the parameter shall be optional, you must
     * set its default value.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @return the created parameter
     */
    public Parameter<String> createStringParameter(String name, String description) {
        return new StringParameter(name, description);
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
     * the returned command. Otherwise a parameter exception is thrown.
     *
     * @param args the user-provided arguments
     * @return the parsed command including all of its parsed children (parameters or subcommand incl. its parameters)
     * @throws ParameterException if the command line arguments could not be parsed successfully, The exception's
     *                            message contains the formatted error and usage guidance according to the error's
     *                            nature.
     */
    public ParsedCommand parse(String... args) throws ParameterException {
        InternalCommand result = command.copy();
        List<String> arguments = new LinkedList<>(Arrays.asList(args));

        // If a subcommand must be present, check for its existence
        if (command.getSubcommands().size() > 0) {

            // Parse the subcommand
            try {
                result.add(parseSubcommand(arguments).copy());
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(e.getMessage()));
            }

            InternalSubcommand subcommand = result.getSubcommands().iterator().next();

            try {
                // Parse all parameters of the subcommand
                parseParameters(arguments, subcommand.getParameters());
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(subcommand.getName(), e.getMessage()));
            }

            // Check if all parameters have values not null. A null value means that a mandatory parameter was not
            // being set via the command line parameters
            for (AbstractParameter<?> parameter : subcommand.getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    throw new ParameterException(helpMenu.printUsage(subcommand.getName(), error));
                }
            }

        } else {

            try {
                // Parse all parameters of the command
                parseParameters(arguments, command.getParameters());
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(e.getMessage()));
            }

            // Check if all parameters have values not null. A null value means that a mandatory parameter was not
            // being set via the command line parameters
            for (AbstractParameter<?> parameter : result.getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    throw new ParameterException(helpMenu.printUsage(error));
                }
            }

        }

        return result;
    }

    /**
     * Parses the parameters of the command line arguments. A parameter must have the structure "--key=value". If either
     * the argument format is invalid, an argument contains a parameter name that is not in the given allowed parameter
     * names, or an argument's value cannot be parsed to the predefined parameter type, a parameter exception is
     * thrown.
     *
     * @param arguments  the user-provided command line arguments
     * @param parameters the parameters that are allowed
     * @throws ParameterException if an arguments contain an unknown parameter name, an argument format is invalid, or a
     *                            argument's value cannot be parsed into the parameter's type
     */
    private void parseParameters(List<String> arguments, Set<AbstractParameter<?>> parameters) throws
            ParameterException {
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
     * Parses the subcommand of the command line arguments. The subcommand must be at index 0 of the given arguments. If
     * either the given arguments are empty or the first argument does not contain any of the predefined given
     * subcommands, a parameter exception is thrown.
     *
     * @param arguments the user-provided command line arguments
     * @return the subcommand of the command line
     * @throws ParameterException if the given arguments do not contain a subcommand at the first index
     */
    private InternalSubcommand parseSubcommand(List<String> arguments) throws ParameterException {
        if (arguments.isEmpty()) {
            throw new ParameterException("No subcommand found");
        }
        if (!command.getSubcommands().stream().map(InternalSubcommand::getName).collect(Collectors.toSet())
                .contains(arguments.get(0))) {
            throw new ParameterException("Unknown subcommand '" + arguments.get(0) + "'");
        }
        return command.getSubcommand(arguments.remove(0));
    }

}
