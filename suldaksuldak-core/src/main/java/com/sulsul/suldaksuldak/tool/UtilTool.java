package com.sulsul.suldaksuldak.tool;

import com.sulsul.suldaksuldak.constant.auth.SDTokken;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static Long getUserPriKeyFromHeader(HttpServletRequest request) {
        return request.getAttribute(SDTokken.USER_PRI_KEY.getText()) == null ? null :
                (Long) request.getAttribute(SDTokken.USER_PRI_KEY.getText());
    }

    public static Pageable getPageable(
            Integer pageNum,
            Integer recordSize
    ) {
        return PageRequest.of(
                pageNum == null || pageNum < 0 ? 0 : pageNum,
                recordSize == null || recordSize < 0 ? 10 : recordSize
        );
    }

    public static Boolean checkLongList(List<Long> longList) {
        return longList != null && !longList.isEmpty();
    }

    public static List<Long> unionLists(List<Long> list1, List<Long> list2) {
        // Create a Set to store unique elements from both lists
        Set<Long> uniqueSet = new HashSet<>();

        // Add elements from the first list to the set
        uniqueSet.addAll(list1);

        // Add elements from the second list to the set
        uniqueSet.addAll(list2);

        // Create a new ArrayList for the result
        return new ArrayList<>(uniqueSet);
    }

    public static List<Long> removeDuplicates(List<Long> inputList) {
        HashSet<Long> uniqueSet = new HashSet<>(inputList);
        return new ArrayList<>(uniqueSet);
    }

    public static List<Long> findOverlappingElements(List<Long> list1, List<Long> list2) {
        List<Long> result = new ArrayList<>(list1);
        result.retainAll(list2);
        return result;
    }
}
