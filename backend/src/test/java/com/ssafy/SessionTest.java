package com.ssafy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.controller.SessionController;
import com.ssafy.api.dto.SessionReqDto;
import com.ssafy.api.service.SessionService;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)// -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@WebMvcTest(SessionController.class)
public class SessionTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    /* SessionService Test */
    @Test
    public void session_create() throws Exception{
        // given
        String name = "test_session";
        LocalDate anniversary = LocalDate.parse("2023-10-05");
        Long userId = 1L;
        Long sessionTypeId = 1L;

        // request dto
        SessionReqDto sessionReqDto = SessionReqDto.builder()
                .name(name)
                .anniversary(anniversary)
                .userId(userId)
                .sessionTypeId(sessionTypeId).build();
        // 일치하는 경우
        Session sessionRes = Session.builder()
                .name(name)
                .anniversary(anniversary)
                .user(User.builder()
                        .userBirthday("12-17")
                        .userEmail("email@naver.com")
                        .userKakaoId(1L)
                        .userNickname("nick-name")
                        .userSocialRefreshToken("refresh-token")
                        .userSocialToken("social-token")
                        .build())
                .sessionTypeId(sessionTypeId).build();
        given(sessionService.createSession(any(SessionReqDto.class))).willReturn(sessionRes);

        // when, then
        this.mockMvc.perform(post("/api/session")
                .content(objectMapper.writeValueAsString(sessionReqDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("session-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("anniversary").description("Session 기념일"),
                                fieldWithPath("name").description("Session 명"),
                                fieldWithPath("sessionTypeId").description("Session 타입 id"),
                                fieldWithPath("userId").description("Session 생성하려는 유저 id").optional()
                        )
                        ));
    }
}