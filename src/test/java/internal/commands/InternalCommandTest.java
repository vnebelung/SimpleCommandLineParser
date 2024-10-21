/*
 * This file is part of ProDisFuzz, modified on 20.10.24, 17:01.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.commands;

import internal.parameters.AbstractParameter;
import internal.parameters.BooleanParameter;
import internal.parameters.IntegerParameter;
import internal.parameters.StringParameter;
import main.Parameter;
import main.Subcommand;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class InternalCommandTest {

    // Add subcommand

    @Test
    public void testAdd() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalCommand.add(internalSubcommand));
        assertEquals(internalCommand.getSubcommands().size(), 1);
        assertTrue(internalCommand.getSubcommands()
                .contains(new InternalSubcommand("subcommandname", "subcommanddescription")));
    }

    @Test
    public void testAdd1() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertThrows(IllegalArgumentException.class, () -> internalCommand.add((Subcommand) null));
    }

    @Test
    public void testAdd2() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new StringParameter("parametername", "parameterdescription")));
        assertThrows(IllegalStateException.class,
                () -> internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription")));
    }

    @Test
    public void testAdd3() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertThrows(IllegalArgumentException.class, () -> internalCommand.add(new DummySubcommand()));
    }

    @Test
    public void testAdd4() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription1")));
        assertFalse(internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription2")));
        assertEquals(internalCommand.getSubcommands().size(), 1);
        assertEquals(internalCommand.getSubcommands().stream().findFirst().get().getDescription(),
                "subcommanddescription1");
    }

    // Add parameter

    @Test
    public void testAdd5() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new IntegerParameter("parametername", "parameterdescription1")));
        assertEquals(internalCommand.getParameters().size(), 1);
        assertTrue(internalCommand.getParameters()
                .contains(new IntegerParameter("parametername", "parameterdescription1")));
    }

    @Test
    public void testAdd6() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new IntegerParameter("parametername", "parameterdescription1")));
        assertFalse(internalCommand.add(new BooleanParameter("parametername", "parameterdescription2")));
        assertEquals(internalCommand.getParameters().size(), 1);
        assertEquals(internalCommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
    }

    @Test
    public void testAdd7() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription")));
        assertThrows(IllegalStateException.class,
                () -> internalCommand.add(new StringParameter("parametername", "testdescription")));
    }

    @Test
    public void testAdd8() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertThrows(IllegalArgumentException.class, () -> internalCommand.add(new DummyParameter()));
    }

    @Test
    public void testCopy() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand
                .add(new StringParameter("parametername1", "parameterdescription1").makeOptional("parametervalue")));
        assertTrue(internalCommand.add(new BooleanParameter("parametername2", "parameterdescription2")));
        InternalCommand copy = internalCommand.copy();
        assertEquals(copy.getName(), "commandname");
        assertEquals(copy.getDescription(), "commanddescription");
        assertEquals(copy.getParameters().size(), 2);
        assertNotNull(copy.getStringParameter("parametername1"));
        assertNotNull(copy.getBooleanParameter("parametername2"));
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername1"))
                .findFirst().get().getDescription(), "parameterdescription1");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername1"))
                .findFirst().get().getValue(), "parametervalue");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername2"))
                .findFirst().get().getDescription(), "parameterdescription2");
        assertNull(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername2"))
                .findFirst().get().getValue());
    }

    @Test
    public void testGetSubcommands() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertEquals(internalCommand.getSubcommands().size(), 0);
    }

    @Test
    public void testGetSubcommands1() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        InternalSubcommand internalSubcommand1 = new InternalSubcommand("subcommandname1", "subcommanddescription1");
        InternalSubcommand internalSubcommand2 = new InternalSubcommand("subcommandname2", "subcommanddescription2");
        assertTrue(internalCommand.add(internalSubcommand1));
        assertTrue(internalCommand.add(internalSubcommand2));
        assertEquals(internalCommand.getSubcommands().size(), 2);
        assertTrue(internalCommand.getSubcommands().contains(internalSubcommand1));
        assertTrue(internalCommand.getSubcommands().contains(internalSubcommand2));
    }

    @Test
    public void testGetSubcommands2() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription1")));
        assertFalse(internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription2")));
        assertEquals(internalCommand.getSubcommands().size(), 1);
        assertEquals(internalCommand.getSubcommands().stream().findFirst().get().getDescription(),
                "subcommanddescription1");
    }

    @Test
    public void testGetSubcommand() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertNull(internalCommand.getSubcommand());
    }

    @Test
    public void testGetSubcommand1() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription")));
        assertNotNull(internalCommand.getSubcommand());
    }

    @Test
    public void testGetSubcommand2() {
        InternalCommand internalCommand = new InternalCommand("commandname", "commanddescription");
        assertTrue(internalCommand.add(new IntegerParameter("parametername2", "parameterdescription")));
        try {
            internalCommand.add(new InternalSubcommand("subcommandname", "subcommanddescription"));
        } catch (IllegalStateException ignored) {
        }
        assertNull(internalCommand.getSubcommand());
    }

    static class DummyParameter implements Parameter<Boolean> {

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public boolean isOptional() {
            return false;
        }

        @Override
        public Boolean getValue() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public AbstractParameter<Boolean> makeOptional(Boolean value) {
            return null;
        }

        @Override
        public String getAllowedValues() {
            return null;
        }
    }

    static class DummySubcommand implements Subcommand {

        @Override
        public boolean add(Parameter<?> parameter) {
            return false;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Set<Parameter<?>> getParameters() {
            return null;
        }
    }
}
