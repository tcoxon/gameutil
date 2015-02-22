package net.bytten.gameutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FileUtil {

    public static String naturalUnits(long fileSize) {
        long kb = fileSize/1024;
        if (kb > 512) {
            double mb = kb / 1024.0;
            if (Math.round(mb*10)/10 >= 10) {
                return String.format("%dMB", (long)Math.round(mb*10)/10);
            } else {
                return String.format("%.1fMB", mb);
            }
        } else {
            return String.format("%dKB", kb);
        }
    }

    public static void copy(File dest, File src) throws IOException {
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void recursiveDelete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c: f.listFiles())
                recursiveDelete(c);
        }
        if (!f.delete())
            throw new IOException("Failed to delete file "+f.getName());
    }

    public static File createTempDirectory(String prefix) throws IOException {
        File f = File.createTempFile(prefix, "");
        
        if (!f.delete())
            throw new IOException("Unable to delete temporary file "+f);
        
        // Race condition, but this is hardly a security-critical app
        
        if (!f.mkdir())
            throw new IOException("Unable to create temporary dir "+f);
        
        return f;
    }

    public static Document readXML(File f) throws IOException, SAXException,
            ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);
        return doc;
    }

    public static byte[] readBytes(InputStream is) throws IOException {
        final int bufferSize = 0x20000;
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(bufferSize);
        int read;
        while (true) {
            read = is.read(buffer);
            if (read == -1)
                break;
            outStream.write(buffer, 0, read);
        }
        return outStream.toByteArray();
    }

    public static String readText(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder buf = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            buf.append(line);
            buf.append('\n');
        }
        return buf.toString();
    }

    public static String readText(File f) throws IOException {
        FileInputStream in = new FileInputStream(f);
        try {
            return readText(in);
        } finally {
            in.close();
        }
    }
    
    public static String readText(URL url) throws IOException {
        try {
            return readText(new File(url.toURI()));
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    public static String[] readLines(File f) throws IOException {
        FileInputStream in = new FileInputStream(f);
        try {
            return readLines(in);
        } finally {
            in.close();
        }
    }

    public static String[] readLines(InputStream is) throws IOException {
        return readText(is).split("\n");
    }

    public static void writeXML(File f, Document doc) throws IOException,
            TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        
        StreamResult out = new StreamResult(f);
        transformer.transform(source, out);
    }

    public static void writeText(File f, String data) throws IOException {
        FileOutputStream os = new FileOutputStream(f);
        try {
            writeText(os, data);
        } finally {
            os.close();
        }
    }

    public static void writeText(OutputStream os, String data)
            throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(data);
        bw.flush();
    }

    public static void cat(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[512];
        int n;
        while ((n = is.read(buf)) != -1) {
            os.write(buf, 0, n);
        }
    }

    public static void unzip(String dirTo, String zipFrom) throws IOException {
        FileInputStream fis = new FileInputStream(zipFrom);
        try {
            unzip(dirTo, fis);
        } finally {
            fis.close();
        }
    }

    public static void unzip(String dirTo, InputStream fis) throws IOException {
        ZipInputStream zis = new ZipInputStream(fis);
        try {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File dest = new File(dirTo+"/"+entry.getName());
                if (entry.isDirectory()) {
                    if (!dest.mkdir()) {
                        throw new IOException("Failed to create directory "+dest.getAbsolutePath());
                    }
                } else {
                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(dest));
                    cat(zis, bos);
                    bos.close();
                }
                zis.closeEntry();
            }
        } finally {
            zis.close();
        }
    }

    @SafeVarargs
    public final static void searchReplace(File f, Pair<String,String>... replacements) throws IOException {
        String text = readText(f);
        for (Pair<String,String> repl: replacements) {
            text = text.replaceAll(repl.first, repl.second);
        }
        writeText(f, text);
    }

    public static void zip(String zipTo, String dirFrom) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipTo);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
        File dir = new File(dirFrom);
        try {
            for (File f: dir.listFiles()) {
                ZipEntry entry = new ZipEntry(f.getName());
                zos.putNextEntry(entry);
                BufferedInputStream bis = new BufferedInputStream(
                        new FileInputStream(f));
                cat(bis, zos);
                bis.close();
                zos.closeEntry();
            }
        } finally {
            zos.close();
        }
    }

}
