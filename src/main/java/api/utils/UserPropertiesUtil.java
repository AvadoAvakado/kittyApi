package api.utils;

import api.enums.PropertyFiles;
import api.exceptions.NotSpecifiedUserIdentifierException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class UserPropertiesUtil extends PropertiesUtil {
    private final PropertyFiles currentFile;
    private static volatile UserPropertiesUtil userPropertiesUtilInstance;

    private UserPropertiesUtil() {
        super(PropertyFiles.USER_PROPERTIES, UserPropertiesUtil.class);
        currentFile = PropertyFiles.USER_PROPERTIES;
    }

    public static UserPropertiesUtil getUserPropertiesUtil() {
        UserPropertiesUtil userPropertiesUtil = userPropertiesUtilInstance;
        if (userPropertiesUtil != null) {
            return userPropertiesUtil;
        }
        synchronized(UserPropertiesUtil.class) {
            if (userPropertiesUtilInstance == null) {
                userPropertiesUtilInstance = new UserPropertiesUtil();
            }
            return userPropertiesUtilInstance;
        }
    }

    public void setSubId(String subId) throws IOException {
        subId = Base64.getEncoder().encodeToString(subId.getBytes());
        try (FileOutputStream fileOutputStream = new FileOutputStream(currentFile.getPath())){
            properties.put("subId", subId);
            properties.store(fileOutputStream, "user was identified");
            fileOutputStream.flush();
        }
    }

    public String getSubId() throws NotSpecifiedUserIdentifierException {
        String subId = getValueByKey("subId");
        if (subId.isEmpty()) {
            throw new NotSpecifiedUserIdentifierException();
        } else {
            return new String(Base64.getDecoder().decode(subId));
        }
    }
}
