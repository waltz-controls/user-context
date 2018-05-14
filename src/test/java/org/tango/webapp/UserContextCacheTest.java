package org.tango.webapp;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/14/18
 */
public class UserContextCacheTest {

    @Test
    public void load() throws Exception {
        UserContextStorage mock = mock(UserContextStorage.class);

        doReturn("some data").when(mock).load("test");

        UserContextCache instance = new UserContextCache(mock);

        String result = instance.load("test");

        assertEquals("some data", result);
    }

    @Test
    public void save() throws Exception {
        UserContextStorage mock = mock(UserContextStorage.class);

        UserContextCache instance = new UserContextCache(mock);

        instance.save("test", "some data");

        String result = instance.load("test");

        assertEquals("some data", result);

        verify(mock, timeout(10)).save("test", "some data");
    }

    @Test
    public void delete() throws Exception {
        UserContextStorage mock = mock(UserContextStorage.class);

        doThrow(IOException.class).when(mock).load(anyString());

        UserContextCache instance = new UserContextCache(mock);

        instance.save("test", "some data");

        instance.delete("test");

        String result = instance.load("test");

        assertNull("some data", result);

        verify(mock, timeout(10)).delete("test");
    }
}