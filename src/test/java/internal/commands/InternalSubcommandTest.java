/*
 * This file is part of ProDisFuzz, modified on 02.11.24, 10:25.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.commands;

import internal.parameters.*;
import main.Parameter;
import org.testng.annotations.Test;

import java.nio.file.Path;

import static org.testng.Assert.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class InternalSubcommandTest {

    @Test
    public void testAdd() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new IntegerParameter("parametername1", "parameterdescription1")));
        assertTrue(internalSubcommand.add(new BooleanParameter("parametername2", "parameterdescription2")));
        assertTrue(internalSubcommand.add(new StringParameter("parametername3", "parameterdescription3")));
        assertTrue(internalSubcommand.add(new PathParameter("parametername4", "parameterdescription4", false)));
        assertTrue(internalSubcommand.add(new DoubleParameter("parametername5", "parameterdescription5")));
        assertEquals(internalSubcommand.getParameters().size(), 5);
        assertTrue(internalSubcommand.getParameters()
                .contains(new IntegerParameter("parametername1", "parameterdescription1")));
        assertTrue(internalSubcommand.getParameters()
                .contains(new BooleanParameter("parametername2", "parameterdescription2")));
        assertTrue(internalSubcommand.getParameters()
                .contains(new StringParameter("parametername3", "parameterdescription3")));
        assertTrue(internalSubcommand.getParameters()
                .contains(new PathParameter("parametername4", "parameterdescription4", false)));
        assertTrue(internalSubcommand.getParameters()
                .contains(new DoubleParameter("parametername5", "parameterdescription5")));
    }

    @Test
    public void testAdd1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription1")));
        assertFalse(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription2")));
        assertFalse(internalSubcommand.add(new StringParameter("parametername", "parameterdescription3")));
        assertFalse(internalSubcommand.add(new PathParameter("parametername", "parameterdescription4", false)));
        assertFalse(internalSubcommand.add(new DoubleParameter("parametername", "parameterdescription5")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
        assertNotNull(internalSubcommand.getIntegerParameter("parametername"));
        assertNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNull(internalSubcommand.getStringParameter("parametername"));
        assertNull(internalSubcommand.getPathParameter("parametername"));
        assertNull(internalSubcommand.getDoubleParameter("parametername"));
    }

    @Test
    public void testAdd2() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription1")));
        assertFalse(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription2")));
        assertFalse(internalSubcommand.add(new StringParameter("parametername", "parameterdescription3")));
        assertFalse(internalSubcommand.add(new PathParameter("parametername", "parameterdescription4", false)));
        assertFalse(internalSubcommand.add(new DoubleParameter("parametername", "parameterdescription5")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
        assertNotNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNull(internalSubcommand.getIntegerParameter("parametername"));
        assertNull(internalSubcommand.getStringParameter("parametername"));
        assertNull(internalSubcommand.getPathParameter("parametername"));
        assertNull(internalSubcommand.getDoubleParameter("parametername"));
    }

    @Test
    public void testAdd3() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new StringParameter("parametername", "parameterdescription1")));
        assertFalse(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription2")));
        assertFalse(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription3")));
        assertFalse(internalSubcommand.add(new PathParameter("parametername", "parameterdescription4", false)));
        assertFalse(internalSubcommand.add(new DoubleParameter("parametername", "parameterdescription5")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
        assertNotNull(internalSubcommand.getStringParameter("parametername"));
        assertNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNull(internalSubcommand.getIntegerParameter("parametername"));
        assertNull(internalSubcommand.getPathParameter("parametername"));
        assertNull(internalSubcommand.getDoubleParameter("parametername"));
    }

    @Test
    public void testAdd4() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new PathParameter("parametername", "parameterdescription1", false)));
        assertFalse(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription2")));
        assertFalse(internalSubcommand.add(new StringParameter("parametername", "parameterdescription3")));
        assertFalse(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription4")));
        assertFalse(internalSubcommand.add(new DoubleParameter("parametername", "parameterdescription5")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
        assertNotNull(internalSubcommand.getPathParameter("parametername"));
        assertNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNull(internalSubcommand.getStringParameter("parametername"));
        assertNull(internalSubcommand.getIntegerParameter("parametername"));
        assertNull(internalSubcommand.getDoubleParameter("parametername"));
    }

    @Test
    public void testAdd5() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new DoubleParameter("parametername", "parameterdescription1")));
        assertFalse(internalSubcommand.add(new BooleanParameter("parametername", "parameterdescription2")));
        assertFalse(internalSubcommand.add(new StringParameter("parametername", "parameterdescription3")));
        assertFalse(internalSubcommand.add(new PathParameter("parametername", "parameterdescription4", false)));
        assertFalse(internalSubcommand.add(new IntegerParameter("parametername", "parameterdescription5")));
        assertEquals(internalSubcommand.getParameters().size(), 1);
        assertEquals(internalSubcommand.getParameters().stream().findFirst().get().getDescription(),
                "parameterdescription1");
        assertNotNull(internalSubcommand.getDoubleParameter("parametername"));
        assertNull(internalSubcommand.getBooleanParameter("parametername"));
        assertNull(internalSubcommand.getStringParameter("parametername"));
        assertNull(internalSubcommand.getPathParameter("parametername"));
        assertNull(internalSubcommand.getIntegerParameter("parametername"));
    }

    @Test
    public void testAdd6() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertThrows(IllegalArgumentException.class, () -> internalSubcommand.add(new DummyParameter()));
    }

    @Test
    public void testAdd7() {
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
        Parameter<Boolean> parameter2 = new BooleanParameter("parametertestname2", "parameterdescription2");
        assertTrue(internalSubcommand.add(parameter2));
        Parameter<Integer> parameter3 = new IntegerParameter("parametertestname3", "parameterdescription3");
        assertTrue(internalSubcommand.add(parameter3));
        Parameter<Integer> parameter4 = new IntegerParameter("parametertestname4", "parameterdescription4");
        assertTrue(internalSubcommand.add(parameter4));
        Parameter<String> parameter5 = new StringParameter("parametertestname5", "parameterdescription5");
        assertTrue(internalSubcommand.add(parameter5));
        Parameter<String> parameter6 = new StringParameter("parametertestname6", "parameterdescription6");
        assertTrue(internalSubcommand.add(parameter6));
        Parameter<Path> parameter7 = new PathParameter("parametertestname7", "parameterdescription7", true);
        assertTrue(internalSubcommand.add(parameter7));
        Parameter<Path> parameter8 = new PathParameter("parametertestname8", "parameterdescription8", true);
        assertTrue(internalSubcommand.add(parameter8));
        Parameter<Double> parameter9 = new DoubleParameter("parametertestname9", "parameterdescription9");
        assertTrue(internalSubcommand.add(parameter9));
        Parameter<Double> parameter10 = new DoubleParameter("parametertestname10", "parameterdescription10");
        assertTrue(internalSubcommand.add(parameter10));

        assertEquals(internalSubcommand.getParameters().size(), 10);
        assertTrue(internalSubcommand.getParameters().contains(parameter1));
        assertTrue(internalSubcommand.getParameters().contains(parameter2));
        assertTrue(internalSubcommand.getParameters().contains(parameter3));
        assertTrue(internalSubcommand.getParameters().contains(parameter4));
        assertTrue(internalSubcommand.getParameters().contains(parameter5));
        assertTrue(internalSubcommand.getParameters().contains(parameter6));
        assertTrue(internalSubcommand.getParameters().contains(parameter7));
        assertTrue(internalSubcommand.getParameters().contains(parameter8));
        assertTrue(internalSubcommand.getParameters().contains(parameter9));
        assertTrue(internalSubcommand.getParameters().contains(parameter10));
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
    public void testGetPathParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertNull(internalSubcommand.getPathParameter("parametername"));
    }

    @Test
    public void testGetPathParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new PathParameter("parametername", "parameterdescription", false)));
        assertNotNull(internalSubcommand.getPathParameter("parametername"));
    }

    @Test
    public void testGetDoubleParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertNull(internalSubcommand.getDoubleParameter("parametername"));
    }

    @Test
    public void testGetDoubleParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(new DoubleParameter("parametername", "parameterdescription")));
        assertNotNull(internalSubcommand.getDoubleParameter("parametername"));
    }

    @Test
    public void testGetName() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertEquals(internalSubcommand.getName(), "subcommandname");
    }

    @Test
    public void testCopy() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("subcommandname", "subcommanddescription");
        assertTrue(internalSubcommand.add(
                new StringParameter("parametername1", "parameterdescription1").makeOptional("p1")));
        assertTrue(internalSubcommand.add(new StringParameter("parametername2", "parameterdescription2")));
        assertTrue(internalSubcommand.add(
                new IntegerParameter("parametername3", "parameterdescription3").makeOptional(3)));
        assertTrue(internalSubcommand.add(new IntegerParameter("parametername4", "parameterdescription4")));
        assertTrue(internalSubcommand.add(
                new BooleanParameter("parametername5", "parameterdescription5").makeOptional(true)));
        assertTrue(internalSubcommand.add(new BooleanParameter("parametername6", "parameterdescription6")));
        assertTrue(internalSubcommand.add(
                new PathParameter("parametername7", "parameterdescription7", false).makeOptional(Path.of("p7"))));
        assertTrue(internalSubcommand.add(new PathParameter("parametername8", "parameterdescription8", false)));
        assertTrue(internalSubcommand.add(
                new DoubleParameter("parametername9", "parameterdescription9").makeOptional(9.9)));
        assertTrue(internalSubcommand.add(new DoubleParameter("parametername10", "parameterdescription10")));

        InternalSubcommand copy = internalSubcommand.copy();

        assertEquals(copy.getName(), "subcommandname");
        assertEquals(copy.getDescription(), "subcommanddescription");
        assertEquals(copy.getParameters().size(), 10);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername1"))
                .findFirst().get().getDescription(), "parameterdescription1");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername1"))
                .findFirst().get().getValue(), "p1");

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername2"))
                .findFirst().get().getDescription(), "parameterdescription2");
        assertNull(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername2"))
                .findFirst().get().getValue(), null);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername3"))
                .findFirst().get().getDescription(), "parameterdescription3");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername3"))
                .findFirst().get().getValue(), 3);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername4"))
                .findFirst().get().getDescription(), "parameterdescription4");
        assertNull(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername4"))
                .findFirst().get().getValue(), null);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername5"))
                .findFirst().get().getDescription(), "parameterdescription5");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername5"))
                .findFirst().get().getValue(), true);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername6"))
                .findFirst().get().getDescription(), "parameterdescription6");
        assertNull(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername6"))
                .findFirst().get().getValue(), null);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername7"))
                .findFirst().get().getDescription(), "parameterdescription7");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername7"))
                .findFirst().get().getValue(), Path.of("p7"));

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername8"))
                .findFirst().get().getDescription(), "parameterdescription8");
        assertNull(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername8"))
                .findFirst().get().getValue(), null);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername9"))
                .findFirst().get().getDescription(), "parameterdescription9");
        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername9"))
                .findFirst().get().getValue(), 9.9);

        assertEquals(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername10"))
                .findFirst().get().getDescription(), "parameterdescription10");
        assertNull(copy.getParameters().stream().filter(parameter -> parameter.getName().equals("parametername10"))
                .findFirst().get().getValue(), null);
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
