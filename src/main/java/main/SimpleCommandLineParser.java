/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:19.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import internal.commands.InternalCommand;
import internal.commands.InternalSubcommand;
import internal.help.Menu;
import internal.parameters.AbstractParameter;

import java.util.*;
import java.util.regex.Pattern;

/**
 * This class represents the command line parser, which handles the command, its subcommands and parameters used in a
 * command line call. The format of this string is as follows: COMMAND SUBCOMMAND --key1 value1 --key2 value2…
 */
@SuppressWarnings("WeakerAccess")
public class SimpleCommandLineParser {

    private final static Pattern PARAMETER_FORMAT = Pattern.compile("--.+");
    private InternalCommand command;
    private Menu helpMenu;

    /**
     * Creates a command line parser.
     */
    public SimpleCommandLineParser() {
        helpMenu = new Menu();
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
     * Sets the command of this parser instance. Usually the command is the name of the executable file (or jar file)
     * that the user will call on the command line. Since this file name does not change and is always the same for
     * every call, you can only define one single command.
     */
    public void setCommand(Command command) {
        if (command.getClass() != InternalCommand.class) {
            throw new IllegalArgumentException("Command was not created by the command line parser");
        }
        this.command = (InternalCommand) command;
    }

    /**
     * Prints formatted usage information into the output string. This only works if the command is already specified.
     *
     * @return the usage help
     */
    public String printHelp() {
        return helpMenu.printUsage(command);
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
        if (!command.getSubcommands().isEmpty()) {

            // Parse the subcommand
            try {
                result.add(parseSubcommand(arguments).copy());
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(command, e.getMessage()));
            }

            InternalSubcommand subcommand = result.getSubcommand();

            try {
                // Parse all parameters of the subcommand
                parseParameters(arguments, subcommand.getParameters());
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(command, subcommand, e.getMessage()));
            }

            // Check if all parameters have values not null. A null value means that a mandatory parameter was not
            // being set via the command line parameters
            for (Parameter<?> parameter : subcommand.getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    throw new ParameterException(helpMenu.printUsage(command, subcommand, error));
                }
            }

        } else {

            try {
                // Parse all parameters of the command
                parseParameters(arguments, result.getParameters());
            } catch (ParameterException e) {
                throw new ParameterException(helpMenu.printUsage(command, e.getMessage()));
            }

            // Check if all parameters have values not null. A null value means that a mandatory parameter was not
            // being set via the command line parameters
            for (Parameter<?> parameter : result.getParameters()) {
                if (parameter.getValue() == null) {
                    String error = "Parameter '" + parameter.getName() + "' is missing";
                    throw new ParameterException(helpMenu.printUsage(command, error));
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
    private void parseParameters(List<String> arguments, Set<Parameter<?>> parameters) throws ParameterException {
        Iterator<String> iterator = arguments.iterator();
        while (iterator.hasNext()) {
            String arg = iterator.next();
            if (!PARAMETER_FORMAT.matcher(arg).matches()) {
                throw new ParameterException("Parameter '%s' has no valid format", arg);
            }
            String key = arg.substring(2);
            if (!iterator.hasNext()) {
                throw new ParameterException("Parameter '%s' has no value", key);
            }
            String value = iterator.next();
            parameters.stream().filter(p -> p.getName().equals(key)).findFirst()
                    .map(parameter -> (AbstractParameter<?>) parameter)
                    .orElseThrow(() -> new ParameterException("Unknown parameter '%s'", key)).setValue(value);
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
        String subcommandName = arguments.remove(0);
        InternalSubcommand result = command.getSubcommand(subcommandName);
        if (result == null) {
            throw new ParameterException("Unknown subcommand '%s'", arguments.get(0));
        }
        return result;
    }

}
