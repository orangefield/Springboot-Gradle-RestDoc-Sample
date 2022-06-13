package site.orangefield.restdoc.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType; // 직접 만든 keystore를 쓰지 않을 때
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.orangefield.restdoc.domain.User;
import site.orangefield.restdoc.util.keystore.MediaTypeImpl;

@AutoConfigureMockMvc
@SpringBootTest
public class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save_test() throws Exception {
        // given
        String content = new ObjectMapper().writeValueAsString(
                User.builder().username("ares").password("1234").email("ares@naver.com").build());

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/user")
                        .content(content)
                        .contentType(MediaTypeImpl.APPLICATION_JSON_UTF8));
        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ares"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ares@naver.com"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_test() throws Exception {
        // given
        Integer id = 1;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/user/" + id));
        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ares"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ares@naver.com"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findAll_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/user/"));
        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].username").value("ares"))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].email").value("ares@naver.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].username").value("vadd"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].email").value("vadd@naver.com"))
                .andDo(MockMvcResultHandlers.print());
    }
}
