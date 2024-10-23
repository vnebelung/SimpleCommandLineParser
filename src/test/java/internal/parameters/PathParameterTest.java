/*
 * This file is part of ProDisFuzz, modified on 23.10.24, 21:24.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.testng.Assert.*;

public class PathParameterTest {

    @Test
    public void testSetValue() throws ParameterException {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        Path dummy = Path.of("DUMMY");
        assertFalse(Files.exists(dummy));
        parameter.setValue("DUMMY");
        assertEquals(parameter.getValue(), dummy.toAbsolutePath().normalize());
    }

    @Test
    public void testSetValue1() throws ParameterException {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        Path dummy = Path.of("/DUMMY");
        assertFalse(Files.exists(dummy));
        parameter.setValue("/DUMMY");
        assertEquals(parameter.getValue(), dummy.toAbsolutePath().normalize());
    }

    @Test
    public void testSetValue2() throws ParameterException, IOException {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", true);
        Path dummy = Files.createTempFile(null, null);
        assertTrue(Files.exists(dummy));
        parameter.setValue(Path.of("").toAbsolutePath().relativize(dummy).toString());
        assertEquals(parameter.getValue(), dummy.toAbsolutePath().normalize());
        Files.delete(dummy);
    }

    @Test
    public void testSetValue3() throws ParameterException, IOException {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", true);
        Path dummy = Files.createTempFile(null, null);
        assertTrue(Files.exists(dummy));
        parameter.setValue(dummy.toAbsolutePath().toString());
        assertEquals(parameter.getValue(), dummy.toAbsolutePath().normalize());
        Files.delete(dummy);
    }

    @Test
    public void testSetValue4() {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", true);
        Path dummy = Path.of("DUMMY");
        assertFalse(Files.exists(dummy));
        assertThrows(ParameterException.class, () -> parameter.setValue(dummy.toString()));
    }

    @Test
    public void testSetValue5() throws IOException {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        Path dummy = Files.createTempFile(null, null);
        assertTrue(Files.exists(dummy));
        assertThrows(ParameterException.class, () -> parameter.setValue(dummy.toAbsolutePath().toString()));
        Files.delete(dummy);
    }

    @Test
    public void testSetValue6() {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        assertThrows(ParameterException.class, () -> parameter.setValue(""));
    }

    @Test
    public void testSetValue7() {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        assertThrows(ParameterException.class, () -> parameter.setValue(null));
    }

    @Test
    public void testCopy() {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        AbstractParameter<Path> copy = parameter.copy();
        assertEquals(copy.getClass(), PathParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
    }

    @Test
    public void testCopy1() throws ParameterException, IOException {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        AbstractParameter<Path> copy = parameter.copy();
        assertEquals(copy.getClass(), PathParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
        assertFalse(Files.exists(Path.of("DUMMY")));
        copy.setValue("DUMMY");
        Path dummy = Files.createTempFile(null, null);
        assertThrows(ParameterException.class, () -> copy.setValue(dummy.toAbsolutePath().toString()));
        Files.delete(dummy);
    }

    @Test
    public void testGetAllowedValues() {
        AbstractParameter<Path> parameter = new PathParameter("parametername", "parameterdescription", false);
        assertEquals(parameter.getAllowedValues(), "");
    }

}
