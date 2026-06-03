package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void indexReturnsTasks() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("tasks"));
    }

    @Test
    void getTasksReturnsFragment() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tasks"))
                .andReturn().getResponse().getContentAsString()
                .contains("task-list");
    }

    @Test
    void addTaskCreatesNewTask() throws Exception {
        mockMvc.perform(post("/tasks").param("title", "New Test Task"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tasks"));
    }
}
