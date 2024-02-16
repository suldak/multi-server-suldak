package com.sulsul.suldaksuldak.service.search;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.search.SearchText;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.search.SearchTextDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.user.UserRepository;
import com.sulsul.suldaksuldak.repo.search.text.SearchTextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final UserRepository userRepository;
    private final SearchTextRepository searchTextRepository;

    public Boolean createSearchLog(
            Long userPriKey,
            String searchText
    ) {
        try {
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty()) return false;
            if (searchText == null || searchText.isBlank()) return false;
            log.info(searchText);
            searchTextRepository.save(
                    SearchText.of(
                            LocalDateTime.now()
                                    .atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + "_" + userPriKey,
                            searchText,
                            user.get()
                    )
            );
            return true;
        } catch (GeneralException e) {
            e.printStackTrace();
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public List<SearchTextDto> getSearchList(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Long userPriKey
    ) {
        try {
            return searchTextRepository.findListByOption(
                    searchStartTime,
                    searchEndTime,
                    userPriKey
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
