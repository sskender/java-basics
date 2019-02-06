package streams;

import java.io.*;


public class MyBuffer {

    BufferedInputStream bis;
    BufferedOutputStream bos;

    public MyBuffer(InputStream is, OutputStream os) {
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(os);
    }

    public void run() {
        try {

            byte[] buffer = new byte[8192];

            while (bis.available() > 0) {
                int readSize = bis.read(buffer);
                bos.write(buffer, 0, readSize);
            }

            bis.close();

            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
