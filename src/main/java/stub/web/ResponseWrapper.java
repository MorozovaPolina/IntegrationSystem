package stub.web;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ResponseWrapper extends HttpServletResponseWrapper {
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
  /* private final String content;

    public String getContent(){
        return this.content;
    }

    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        content = IOUtils.toString(response.getHeader(response.getHeaders(1)));
    }

    @Override
    public ServletOutputStream getOutputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        return new ServletOutputStream(){
            @Override
            public void write(int b) throws IOException {

            }

            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            public void setReadListener(ReadListener readListener) {}

            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }*/
}
