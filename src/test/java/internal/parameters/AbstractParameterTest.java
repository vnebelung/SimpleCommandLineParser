/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:50.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AbstractParameterTest {

    @Test
    public void testGetDescription() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertEquals(parameter.getDescription(), "parameterdescription");
    }

    @Test
    public void testGetDescription1() {
        assertThrows(IllegalArgumentException.class, () -> new BooleanParameter("parametername", null));
    }

    @Test
    public void testGetDescription2() {
        assertThrows(IllegalArgumentException.class, () -> new BooleanParameter("parametername", ""));
    }

    @Test
    public void testGetValue() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertNull(parameter.getValue());
    }

    @Test
    public void testMakeOptional() {
        AbstractParameter<Integer> parameter =
                new IntegerParameter("parametername", "parameterdescription").makeOptional(6);
        assertEquals(parameter.getValue().intValue(), 6);
        assertTrue(parameter.isOptional());
    }

    @Test
    public void testMakeOptional1() {
        AbstractParameter<Integer> parameter =
                new IntegerParameter("parametername", "parameterdescription").makeOptional(6);
        assertThrows(IllegalArgumentException.class, () -> parameter.makeOptional(null));
        assertEquals(parameter.getValue().intValue(), 6);
    }

    @Test
    public void testIsOptional() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertFalse(parameter.isOptional());
    }

    @Test
    public void testIsOptional1() {
        AbstractParameter<Boolean> parameter =
                new BooleanParameter("parametername", "parameterdescription").makeOptional(false);
        assertTrue(parameter.isOptional());
    }

    @Test
    public void testGetName() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parameter-name", "parameterdescription");
        assertEquals(parameter.getName(), "parameter-name");
    }

    @Test
    public void testGetName1() {
        assertThrows(IllegalArgumentException.class, () -> new BooleanParameter(null, "parameterdescription"));
    }

    @Test
    public void testGetName2() {
        assertThrows(IllegalArgumentException.class, () -> new BooleanParameter("", "parameterdescription"));
    }

    @Test
    public void testGetName3() {
        assertThrows(IllegalArgumentException.class, () -> new BooleanParameter("a--a", "parameterdescription"));
    }

    @Test
    public void testSetCastedValue() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertNull(parameter.getValue());
        parameter.setCastedValue(4);
        assertEquals(parameter.getValue(), Integer.valueOf(4));
    }

    @Test
    public void testEquals() {
        AbstractParameter<Integer> parameter1 =
                new IntegerParameter("parametername", "parameterdescription1").makeOptional(4);
        AbstractParameter<Boolean> parameter2 = new BooleanParameter("parametername", "parameterdescription2");
        //noinspection AssertEqualsBetweenInconvertibleTypesTestNG
        assertEquals(parameter2, parameter1);
    }

    @Test
    public void testEquals1() {
        AbstractParameter<Integer> parameter1 = new IntegerParameter("parametername1", "parameterdescription");
        AbstractParameter<Integer> parameter2 = new IntegerParameter("parametername2", "parameterdescription");
        assertNotEquals(parameter2, parameter1);
    }

    @Test
    public void testTestHashCode() {
        AbstractParameter<Integer> parameter1 =
                new IntegerParameter("parametername", "parameterdescription1").makeOptional(4);
        AbstractParameter<Boolean> parameter2 = new BooleanParameter("parametername", "parameterdescription2");
        assertEquals(parameter1.hashCode(), parameter2.hashCode());
    }

    @Test
    public void testTestHashCode1() {
        AbstractParameter<Integer> parameter1 =
                new IntegerParameter("parametername1", "parameterdescription").makeOptional(4);
        AbstractParameter<Integer> parameter2 = new IntegerParameter("parametername2", "parameterdescription");
        assertNotEquals(parameter1.hashCode(), parameter2.hashCode());
    }
}
