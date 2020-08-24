package api.utils;

import api.Enums.PropertyFiles;
import api.exceptions.NotSpecifiedUserIdentifierException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class PropertiesUtil {
    private static final Logger logger;
    protected final Properties properties = new Properties();
    static {
        BasicConfigurator.configure();
        logger = LogManager.getLogger(PropertiesUtil.class);
    }

    public PropertiesUtil(final PropertyFiles propertyFile) {
        try (FileInputStream streamToFile = new FileInputStream(propertyFile.getPath())){
            properties.load(streamToFile);
        } catch (IOException e) {
            logger.warn(String.format("There is an issue in reading %s property file.\n%s",
                    propertyFile, Arrays.toString(e.getStackTrace())));
        }
    }

    public String getValueByKey(final String key) {
        String propertyValue = null;
        if(properties.containsKey(key)) {
            propertyValue = properties.getProperty(key);
        } else {
            logger.info(String.format("There is no value with '%s' key ", key));
        }
        return propertyValue;
    }
}
