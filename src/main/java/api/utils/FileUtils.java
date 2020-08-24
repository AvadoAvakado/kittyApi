package api.utils;

import api.Enums.PropertyFiles;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class FileUtils {
    private static final PropertiesUtil propertiesUtil = new PropertiesUtil(PropertyFiles.API_PROPERTIES);
    private static final Logger logger;

    static {
        BasicConfigurator.configure();
        logger = LogManager.getLogger(FileUtils.class);
    }

    public static void saveFileFromUrl(String fileUrl, String destinationFile){
        URLConnection connection = getUrlConnection(fileUrl);
        try {
            connection.addRequestProperty("User-Agent", propertiesUtil.getValueByKey("userAgentMozilla"));
            InputStream is = connection.getInputStream();
            destinationFile = String.format("%s%s%s", "kittyPictures",  File.separator, destinationFile);
            OutputStream os = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            logger.info(String.format("Error in saving file from %s to %s\n%s",
                    fileUrl, destinationFile, Arrays.toString(e.getStackTrace())));
        }
    }

    private static URLConnection getUrlConnection(final String urlString) {
        URLConnection uc = null;
        try {
            URL url = new URL(urlString);
             uc = url.openConnection();
        } catch (MalformedURLException e) {
            logger.info(String.format("Error in creating URL\n%s", Arrays.toString(e.getStackTrace())));
        } catch (IOException e) {
            logger.info(String.format("Error in opening URL connection\n%s", Arrays.toString(e.getStackTrace())));
        }
        return uc;
    }
}
