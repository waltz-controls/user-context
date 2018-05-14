package org.tango.webapp;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/14/18
 */
public class FileSystemUserContextStorageTest {

    @Test
    public void simple() throws Exception {
        FileSystemUserContextStorage instance = new FileSystemUserContextStorage(Paths.get("target"));
        instance.save("test","some data");

        String result = instance.load("test");
        assertEquals("some data", result);

        instance.delete("test");

        assertFalse(Files.exists(Paths.get("data", "test")));
    }
}