package com.wtgroup.commons;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2018-01-05-0:24
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.shiro.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceUtils {
    public static final String CLASSPATH_PREFIX = "classpath:";
    public static final String URL_PREFIX = "url:";
    public static final String FILE_PREFIX = "file:";
    private static final Logger log = LoggerFactory.getLogger(org.apache.shiro.io.ResourceUtils.class);

    private ResourceUtils() {
    }

    public static boolean hasResourcePrefix(String resourcePath) {
        return resourcePath != null && (resourcePath.startsWith("classpath:") || resourcePath.startsWith("url:") || resourcePath.startsWith("file:"));
    }

    public static boolean resourceExists(String resourcePath) {
        InputStream stream = null;
        boolean exists = false;

        try {
            stream = getInputStreamForPath(resourcePath);
            exists = true;
        } catch (IOException var12) {
            stream = null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException var11) {
                    ;
                }
            }

        }

        return exists;
    }

    public static InputStream getInputStreamForPath(String resourcePath) throws IOException {
        InputStream is;
        if (resourcePath.startsWith("classpath:")) {
            is = loadFromClassPath(stripPrefix(resourcePath));
        } else if (resourcePath.startsWith("url:")) {
            is = loadFromUrl(stripPrefix(resourcePath));
        } else if (resourcePath.startsWith("file:")) {
            is = loadFromFile(stripPrefix(resourcePath));
        } else {
            is = loadFromFile(resourcePath);
        }

        if (is == null) {
            throw new IOException("Resource [" + resourcePath + "] could not be found.");
        } else {
            return is;
        }
    }

    private static InputStream loadFromFile(String path) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Opening file [" + path + "]...");
        }

        return new FileInputStream(path);
    }

    private static InputStream loadFromUrl(String urlPath) throws IOException {
        log.debug("Opening url {}", urlPath);
        URL url = new URL(urlPath);
        return url.openStream();
    }

    private static InputStream loadFromClassPath(String path) {
        log.debug("Opening resource from class path [{}]", path);
        return ClassUtils.getResourceAsStream(path);
    }

    private static String stripPrefix(String resourcePath) {
        return resourcePath.substring(resourcePath.indexOf(":") + 1);
    }

    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException var2) {
                log.warn("Error closing input stream.", var2);
            }
        }

    }
}
