package api.utils;

import api.Enums.PropertyFiles;
import api.exceptions.NotSpecifiedUserIdentifierException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class UserPropertiesUtil extends PropertiesUtil {
    private final PropertyFiles currentFile;

    public UserPropertiesUtil() {
        super(PropertyFiles.USER_PROPERTIES);
        currentFile = PropertyFiles.USER_PROPERTIES;
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
