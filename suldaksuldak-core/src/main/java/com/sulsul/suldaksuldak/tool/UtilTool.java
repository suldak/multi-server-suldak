package com.sulsul.suldaksuldak.tool;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilTool {
    public static String encryptPassword (
            @NotNull String password,
            @NotNull String id
    ) throws Exception
    {
        byte[] hashValue = null;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(id.getBytes());
        hashValue = md.digest(password.getBytes());
        return new String(Base64.encodeBase64(hashValue, false));
    }

    public static List<Long> companyIdListParser(String companyIdList) {
        if (companyIdList == null) return null;
        return Stream.of(companyIdList.split(","))
                .map(Long::parseLong).collect(Collectors.toList());
    }

    public static List<String> companyIdListParserToString(String companyIdList) {
        if (companyIdList == null) return null;
        return Stream.of(companyIdList.split(",")).collect(Collectors.toList());
    }
}
