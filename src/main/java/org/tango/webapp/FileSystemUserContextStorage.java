package org.tango.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/14/18
 */
public class FileSystemUserContextStorage implements UserContextStorage {
    @Override
    public void save(String userName, String data) throws IOException {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public void delete(String userName) throws IOException {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public String load(String userName) throws IOException {
        Path dataFile = Paths.get("data").resolve(userName);
        if(Files.exists(dataFile))
            return new String(Files.readAllBytes(dataFile), "UTF-8");
        else
            return null;
    }
}
