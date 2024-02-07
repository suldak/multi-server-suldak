package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.comment.PartyCommentReq;
import com.sulsul.suldaksuldak.dto.party.comment.PartyCommentRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.party.PartyCommentService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/party/comment")
@Api(tags = "[MAIN] 모임 댓글 관리")
public class PartyCommentController {
    private final PartyCommentService partyCommentService;

    @PostMapping("/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 댓글 작성",
            notes = "모임의 댓글을 작성합니다. (Body에 commentPriKey 값이 NULL이 아니면 대댓글을 작성합니다.)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", required = true, dataTypeClass = Long.class)
    })
    public ApiDataResponse<Boolean> createComment(
            HttpServletRequest request,
            @PathVariable Long partyPriKey,
            @RequestBody PartyCommentReq partyCommentReq
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        if (partyCommentReq.getCommentPriKey() != null &&
                !partyCommentReq.getCommentPriKey().isBlank())
        {
            // 대댓글 작성
            return ApiDataResponse.of(
                    partyCommentService.createChildrenComment(
                            userPriKey,
                            partyPriKey,
                            partyCommentReq.getCommentPriKey(),
                            partyCommentReq.getComment()
                    )
            );
        } else {
            return ApiDataResponse.of(
                    partyCommentService.createComment(
                            userPriKey,
                            partyPriKey,
                            partyCommentReq.getComment()
                    )
            );
        }
    }

    @PutMapping("/{commentPriKey}")
    @ApiOperation(
            value = "모임 댓글 수정",
            notes = "모임의 댓글을 수정합니다. (Body에 commentPriKey 값은 없어도 됩니다.)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentPriKey", value = "댓글 기본키", required = true, dataTypeClass = String.class)
    })
    public ApiDataResponse<Boolean> modifiedComment(
            HttpServletRequest request,
            @PathVariable String commentPriKey,
            @RequestBody PartyCommentReq partyCommentReq
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                partyCommentService.modifiedComment(
                        userPriKey,
                        commentPriKey,
                        partyCommentReq.getComment()
                )
        );
    }

    @DeleteMapping("/{commentPriKey}")
    @ApiOperation(
            value = "모임 댓글 삭제",
            notes = "모임의 댓글을 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentPriKey", value = "댓글 기본키", required = true, dataTypeClass = String.class)
    })
    public ApiDataResponse<Boolean> deleteComment(
            HttpServletRequest request,
            @PathVariable String commentPriKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                partyCommentService.deleteComment(
                        userPriKey,
                        commentPriKey
                )
        );
    }

    @GetMapping("/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 댓글 목록 조회",
            notes = "모임의 댓글 목록을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10")
    })
    public ApiDataResponse<Page<PartyCommentRes>> getPartyComment(
            @PathVariable Long partyPriKey,
            Integer pageNum,
            Integer recordSize
    ) {
        return ApiDataResponse.of(
                partyCommentService.getPartyCommentList(
                        partyPriKey,
                        UtilTool.getPageable(pageNum, recordSize)
                )
        );
    }

    @GetMapping("/children/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 대댓글 목록 조회",
            notes = "모임의 대댓글 목록을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "commentPriKey", value = "댓글 기본키", required = true, dataTypeClass = String.class)
    })
    public ApiDataResponse<List<PartyCommentRes>> getChildrenComment(
            @PathVariable Long partyPriKey,
            String commentPriKey
    ) {
        return ApiDataResponse.of(
                partyCommentService.getPartyChildrenComment(
                            partyPriKey,
                            commentPriKey,
                            1
                    )
                    .stream()
                    .map(PartyCommentRes::from)
                    .toList()
        );
    }

}
