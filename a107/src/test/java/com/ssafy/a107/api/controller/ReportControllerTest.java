package com.ssafy.a107.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a107.api.request.ReportReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void saveTest() throws Exception{
        ReportReq req = ReportReq.builder()
                .reporterSeq(0L)
                .targetSeq(1L)
                .reportType((byte)0)
                .reportDetail("그냥 맘에 안들어요")
                .build();

        mvc.perform(
                MockMvcRequestBuilders.post("/api/reports")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req))

                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

    }

}
