package com.sulsul.suldaksuldak.tool;

import com.sulsul.suldaksuldak.constant.auth.SDTokken;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class UtilTool {
    public static String getLocalDateTimeString() {
        return Long.toString(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli());
    }
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

    /**
     * 사용자의 Request Header 에서
     * Token을 분석하여 userPriKey를 조회
     */
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
        Set<Long> uniqueSet = new HashSet<>();
        uniqueSet.addAll(list1);
        uniqueSet.addAll(list2);
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

    public static HashMap<Long, Integer> generateCountedHashMap(List<Long> liquorPriKeyList) {
        HashMap<Long, Integer> countedMap = new HashMap<>();
        for (Long key : liquorPriKeyList) {
            if (key != null) {
                if (countedMap.containsKey(key)) {
                    countedMap.put(key, countedMap.get(key) + 1);
                } else {
                    countedMap.put(key, 1);
                }
            }
        }
        return countedMap;
    }

    public static List<Long> selectKeysWithHighValues(HashMap<Long, Integer> inputMap) {
        if (inputMap.isEmpty()) return List.of();
        // Convert the map entries to a list of Map.Entry<Long, Integer>
        List<Map.Entry<Long, Integer>> entryList = new ArrayList<>(inputMap.entrySet());

        // Sort the entries in descending order of values
        entryList.sort(Map.Entry.<Long, Integer>comparingByValue().reversed());

        int limit = Math.min(3, entryList.size());
        Random random = new Random();
        if (entryList.size() > 3) {
            limit = random.nextInt(3) + 1;
        } else {
            limit = random.nextInt(inputMap.size()) + 1;
        }

        return entryList.subList(0, limit).stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
