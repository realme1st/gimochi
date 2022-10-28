package com.ssafy;

import com.ssafy.api.controller.SessionController;
import com.ssafy.api.dto.SessionReqDto;
import com.ssafy.api.service.SessionService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.db.entity.Session;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class SessionTest {
    /*
    *  assertEquals(a,b);   객체 A와 B의 실제 값이 일치한지 확인한다.
    * assertSame(a, b); 객체 A와 B가 같은 객체임을 확인한다.
    * assertEquals      메서드는 두 객체의 값의 비교
    * assertSame        메서드는 두 객체가 동일한지 객체의 비교 (== 연산자와 같다)
    * assertTrue(a);    조건 A가 참인가를 확인한다.
    * assertNotNull(a); 객체 A가 null이 아님을 확인한다.
    * */
    @Autowired
    SessionService sessionService;

    @Autowired
    SessionController sessionController;

    @Test
    public void createServiceTest() {
        Session session = sessionService.createSession(new SessionReqDto() {{
            setUserId(1L);
            setName("test");
            setSessionTypeId(1L);
            setCreateTime(LocalDateTime.now());
            setExpireTime(LocalDateTime.now());
            setAnniversary(LocalDate.now());
        }});
        assertNotNull(session);
        assertEquals("test", session.getName());
    }

    @Test
    public void createControllerTest(){
        ResponseEntity<? extends BasicResponse> res = sessionController.createSession(new SessionReqDto() {{
            setUserId(1L);
            setName("test123");
            setSessionTypeId(1L);
            setCreateTime(LocalDateTime.now());
            setExpireTime(LocalDateTime.now());
            setAnniversary(LocalDate.now());
        }});
        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

    }

//    @Test
//    @TestDescription("비정상 userId로 세션 생성")
}
