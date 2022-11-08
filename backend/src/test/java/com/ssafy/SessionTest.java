package com.ssafy;

import com.ssafy.api.controller.SessionController;
import com.ssafy.api.dto.SessionReqDto;
import com.ssafy.api.service.SessionService;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@WebMvcTest(SessionController.class)
public class SessionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private UserRepository userRepository;

    /* SessionService Test */
    @Test
    void session_create() throws Exception{
        // given
        String name = "test_session";
        LocalDate anniversary = LocalDate.parse("2023-10-05");
        Long userId = 1L;
        Long sessionTypeId = 1L;

        User user = userRepository.findByUserId(userId).get();

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
                .user(user)
                .sessionTypeId(sessionTypeId).build();
        given(sessionService.createSession(any(SessionReqDto.class))).willReturn(sessionRes);

        // when

        // then
    }

//    @Test
//    public void test () {
//
//    }
}