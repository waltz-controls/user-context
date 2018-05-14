package org.tango.webapp;

import java.io.IOException;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/14/18
 */
public interface UserContextStorage {
    void save(String userName, String data) throws IOException;

    void delete(String userName) throws IOException;

    String load(String userName) throws IOException;
}
