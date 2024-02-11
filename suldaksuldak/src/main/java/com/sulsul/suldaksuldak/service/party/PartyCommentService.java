package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyComment;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.party.comment.PartyCommentDto;
import com.sulsul.suldaksuldak.dto.party.comment.PartyCommentRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.comment.PartyCommentRepository;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyCommentService {
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyCommentRepository partyCommentRepository;

    /**
     * 댓글 생성
     */
    public Boolean createComment(
            Long userPriKey,
            Long partyPriKey,
            String comment
    ) {
        try {
            if (userPriKey == null || partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키를 찾을 수 없습니다."
                );
            if (comment == null || comment.isBlank())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "comment는 NULL이 될 수 없습니다."
                );
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 유저를 찾을 수 없습니다."
                );
            Optional<Party> party = partyRepository.findById(partyPriKey);
            if (party.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임을 찾을 수 없습니다."
                );
            String dateStr = UtilTool.getLocalDateTimeString();
            String priKey = dateStr + "_" + party.get().getId() + "_" + user.get().getId();
            partyCommentRepository.save(
                    PartyComment.of(
                            priKey,
                            comment,
                            user.get(),
                            party.get(),
                            priKey,
                            0,
                            false,
                            false
                    )
            );
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 대댓글 작성
     */
    public Boolean createChildrenComment(
            Long userPriKey,
            Long partyPriKey,
            String commentPriKey,
            String comment
    ) {
        try {
            if (userPriKey == null || partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키를 찾을 수 없습니다."
                );
            if (comment == null || comment.isBlank())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "comment는 NULL이 될 수 없습니다."
                );
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 유저를 찾을 수 없습니다."
                );
            Optional<Party> party = partyRepository.findById(partyPriKey);
            if (party.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임을 찾을 수 없습니다."
                );
            Optional<PartyComment> partyComment = partyCommentRepository.findById(commentPriKey);
            if (partyComment.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "댓글 정보를 찾을 수 없습니다."
                );
            if (partyComment.get().getIsDelete())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 삭제된 댓글 입니다."
                );
            String dateStr = UtilTool.getLocalDateTimeString();
            String priKey = dateStr + "_" + party.get().getId() + "_" + user.get().getId();
            // 먼저 대댓글 저장
            partyCommentRepository.save(
                    PartyComment.of(
                            priKey,
                            comment,
                            user.get(),
                            party.get(),
                            partyComment.get().getId(),
                            partyComment.get().getCommentDep() + 1,
                            false,
                            false
                    )
            );
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 댓글 수정
     */
    public Boolean modifiedComment(
            Long userPriKey,
            String commentPriKey,
            String comment
    ) {
        try {
            if (commentPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 누락되었습니다."
                );
            if (comment == null || comment.isBlank())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "comment가 NULL 입니다."
                );
            Optional<PartyComment> partyComment = partyCommentRepository.findById(commentPriKey);
            if (partyComment.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "댓글 정보를 찾을 수 없습니다."
                );
            if (partyComment.get().getIsDelete())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 삭제된 댓글 입니다."
                );
            if (!userPriKey.equals(partyComment.get().getUser().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "댓글은 작성한 유저만 수정할 수 있습니다."
                );
            partyComment.get().setComment(comment);
            partyComment.get().setIsModified(true);
            partyCommentRepository.save(partyComment.get());
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 댓글 삭제
     */
    public Boolean deleteComment(
            Long userPriKey,
            String commentPriKey
    ) {
        try {
            if (commentPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 누락되었습니다."
                );
            Optional<PartyComment> partyComment = partyCommentRepository.findById(commentPriKey);
            if (partyComment.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "댓글 정보를 찾을 수 없습니다."
                );
            if (!userPriKey.equals(partyComment.get().getUser().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "댓글은 작성한 유저만 삭제할 수 있습니다."
                );
            if (partyComment.get().getIsDelete())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 삭제된 댓글 입니다."
                );
            partyComment.get().setIsDelete(true);
            partyCommentRepository.save(partyComment.get());
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    public Page<PartyCommentRes> getPartyCommentList(
            Long partyPriKey,
            Pageable pageable
    ) {
        try {
            if (partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 기본키가 누락되었습니다."
                );
            Page<PartyCommentDto> partyCommentDtos =
                    partyCommentRepository.findByPartyPriKey(
                            partyPriKey,
                            pageable
                    );
            return new PageImpl<>(
                    partyCommentDtos.getContent()
                            .stream()
                            .map(dto ->
                                    PartyCommentRes.from(
                                            dto,
                                            getPartyChildrenComment(
                                                    partyPriKey,
                                                    dto.getGroupComment(),
                                                    dto.getCommentDep() + 1
                                            )
                                    )
                            )
                            .toList(),
                    partyCommentDtos.getPageable(),
                    partyCommentDtos.getTotalElements()
            );
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }

    public List<PartyCommentDto> getPartyChildrenComment(
            Long partyPriKey,
            String groupComment,
            Integer commentDep
    ) {
        try {
            return partyCommentRepository.findByCommentDep(
                    partyPriKey,
                    groupComment,
                    commentDep
            );
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }
}
