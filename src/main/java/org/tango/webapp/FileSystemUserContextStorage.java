package org.tango.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/14/18
 */
public class FileSystemUserContextStorage implements UserContextStorage {

    private final Path root;

    public FileSystemUserContextStorage(Path root) {
        this.root = root;
    }

    @Override
    public void save(String userName, String data) throws IOException {
        Path dataFile = getPath(userName);
        Files.write(dataFile, data.getBytes("UTF-8"));
    }

    @Override
    public void delete(String userName) throws IOException {
        Path dataFile = getPath(userName);
        Files.deleteIfExists(dataFile);
    }

    @Override
    public String load(String userName) throws IOException {
        Path dataFile = getPath(userName);
        if(Files.exists(dataFile))
            return new String(Files.readAllBytes(dataFile), "UTF-8");
        else
            throw new IOException("User data was not found: " + userName);
    }

    private Path getPath(String userName) {
        return root.resolve(userName);
    }
}
