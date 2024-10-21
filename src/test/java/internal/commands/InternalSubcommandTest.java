/*
 * This file is part of ProDisFuzz, modified on 20.10.24, 17:02.
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
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class InternalSubcommandTest {

    @Test
    public void testAdd() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription1")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertTrue(internalSubcommand.getParameters()
                .contains(new IntegerParameter("parametername", "parameterdescription1")));
    }

    @Test
    public void testAdd1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription1")));
        assertFalse(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription2")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
        assertNotNull(internalSubcommand.getIntegerParameter("parametername"));
        assertNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNull(internalSubcommand.getStringParameter("parametername"));
    }

    @Test
    public void testAdd2() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription2")));
        assertFalse(internalSubcommand.add(new StringParameter("parametername", "parameterdescription1")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription2");
        assertNull(internalSubcommand.getIntegerParameter("parametername"));
        assertNotNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNull(internalSubcommand.getStringParameter("parametername"));
    }

    @Test
    public void testAdd3() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new StringParameter("parametername", "parameterdescription1")));
        assertFalse(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription2")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
        assertNull(internalSubcommand.getIntegerParameter("parametername"));
        assertNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNotNull(internalSubcommand.getStringParameter("parametername"));
    }

    @Test
    public void testAdd4() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertThrows(IllegalArgumentException.class, () -> internalSubcommand.add(new DummyParameter()));
    }

    @Test
    public void testAdd5() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertThrows(IllegalArgumentException.class, () -> internalSubcommand.add(null));
    }

    @Test
    public void testGetDescription() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertEquals(internalSubcommand.getDescription(), "subcommanddescription");
    }

    @Test
    public void testGetParameters() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertEquals(internalSubcommand.getParameters().size(), 0);
    }

    @Test
    public void testGetParameters1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        Parameter<Boolean> parameter1 = new BooleanParameter("parametertestname1", "parameterdescription1");
        assertTrue(internalSubcommand.add(parameter1));
        Parameter<Integer> parameter2 = new IntegerParameter("parametertestname2", "parameterdescription2");
        assertTrue(internalSubcommand.add(parameter2));
        Parameter<String> parameter3 = new StringParameter("parametertestname3", "parameterdescription3");
        assertTrue(internalSubcommand.add(parameter3));
        Parameter<String> parameter4 = new StringParameter("parametertestname4", "parameterdescription4");
        assertTrue(internalSubcommand.add(parameter4));

        assertEquals(internalSubcommand.getParameters().size(), 4);
        assertTrue(internalSubcommand.getParameters().contains(parameter1));
        assertTrue(internalSubcommand.getParameters().contains(parameter2));
        assertTrue(internalSubcommand.getParameters().contains(parameter3));
        assertTrue(internalSubcommand.getParameters().contains(parameter4));
    }

    @Test
    public void testGetIntegerParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertNull(internalSubcommand.getIntegerParameter("parametername"));
    }

    @Test
    public void testGetIntegerParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription")));
        assertNotNull(internalSubcommand.getIntegerParameter("parametername"));
    }

    @Test
    public void testGetBooleanParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertNull(internalSubcommand.getBooleanParameter("parametername"));
    }

    @Test
    public void testGetBooleanParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription")));
        assertNotNull(internalSubcommand.getBooleanParameter("parametername"));
    }

    @Test
    public void testGetStringParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertNull(internalSubcommand.getStringParameter("parametername"));
    }

    @Test
    public void testGetStringParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new StringParameter("parametername", "parameterdescription")));
        assertNotNull(internalSubcommand.getStringParameter("parametername"));
    }

    @Test
    public void testGetName() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertEquals(internalSubcommand.getName(), "subcommandname");
    }

    @Test
    public void testCopy() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new StringParameter("parametername1", "parameterdescription1")));
        assertTrue(internalSubcommand
                .add(new IntegerParameter("parametername2", "parameterdescription2").makeOptional(4)));
        InternalSubcommand copy = internalSubcommand.copy();
        assertEquals(copy.getName(), "subcommandname");
        assertEquals(copy.getDescription(), "subcommanddescription");
        assertEquals(copy.getParameters().size(), 2);
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername1"))
                .findFirst().get().getDescription(), "parameterdescription1");
        assertNull(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername1"))
                .findFirst().get().getValue());
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername2"))
                .findFirst().get().getDescription(), "parameterdescription2");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername2"))
                .findFirst().get().getValue(), 4);
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

}
