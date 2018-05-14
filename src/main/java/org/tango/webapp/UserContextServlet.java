package org.tango.webapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPOutputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/7/18
 */
public class UserContextServlet extends HttpServlet {
    //TODO inject
    private final AtomicReference<UserContextStorage> storage = new AtomicReference<>();

    @Override
    public void init() throws ServletException {
        storage.set(new UserContextCache(
                        new FileSystemUserContextStorage(
                            Paths.get(getServletContext().getRealPath("data")))));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String userName = req.getParameter("id");
        checkNotNull(userName);

        response.setHeader("Content-Encoding", "gzip");
        String data = storage.get().load(userName);


        OutputStream outA = response.getOutputStream();
        PrintWriter outWriter = new PrintWriter(new GZIPOutputStream(outA), false);

        response.setHeader("Content-transfer-encoding","base64");
        outWriter.print(data);
        outWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("id");
        checkNotNull(userName);
        String data = req.getParameter("data");

        if(data == null || data.isEmpty()){
            storage.get().delete(userName);
        } else {
            storage.get().save(userName, data);
        }
    }
}
