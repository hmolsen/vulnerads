package de.cqrity.vulnerapp.util;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.UnixCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.*;

@Service
public class VirusScanner {

    @Autowired
    ServletContext servletContext;

    private File file;

    private boolean virusFree = false;

    VirusScanner() {
    }

    public de.cqrity.vulnerapp.util.VirusScanner forFile(File file) {
        this.file = file;
        return this;
    }

    public de.cqrity.vulnerapp.util.VirusScanner scan() {
        try {
            File antivir = new ServletContextResource(servletContext, "/resources/antivir/antivir.sh").getFile();

            String absolutePath = ESAPI.encoder().encodeForOS(new UnixCodec(), file.getAbsolutePath());
            String username = ESAPI.encoder().encodeForOS(new UnixCodec(), SecurityContextHolder.getContext().getAuthentication().getName());

            String[] command = new String[]{
                    "/bin/sh",
                    "-c",
                    "\"" + antivir.getAbsolutePath() + "\" \"" + absolutePath + "\" \"" + username
            };
            Process proc = Runtime.getRuntime().exec(command, null, null);

            new de.cqrity.vulnerapp.util.VirusScanner.StreamGobbler(proc.getErrorStream(), "STDERR").start();
            new de.cqrity.vulnerapp.util.VirusScanner.StreamGobbler(proc.getInputStream(), "STDOUT").start();

            int exitCode = proc.waitFor();
            if (exitCode == 0) {
                virusFree = true;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean isVirusFree() {
        return virusFree;
    }

    private static class StreamGobbler extends Thread {
        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(type + ">" + line);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
