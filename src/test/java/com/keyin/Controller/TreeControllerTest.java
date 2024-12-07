package com.keyin.Controller;

import com.keyin.Service.TreeService;
import com.keyin.Tree.BinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TreeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TreeService treeService;

    @InjectMocks
    private TreeController treeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(treeController).build();
    }

    // Test the enterNumbers method
    @Test
    public void testEnterNumbers() throws Exception {
        // Perform a GET request to the /enter-numbers endpoint
        mockMvc.perform(get("/enter-numbers"))
                // Expect the status to be OK (200)
                .andExpect(status().isOk())
                // Expect the content type to be compatible with text/plain
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                // Expect the response content to contain the string "Enter numbers (comma-separated):"
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Enter numbers (comma-separated):")));
    }

    @Test
    public void testProcessNumbers() throws Exception {
        // Define a string of numbers to be processed
        String numbers = "50,30,70,20,40,60,80";

        // Create a new BinarySearchTree object
        BinarySearchTree tree = new BinarySearchTree();

        // Mock the treeService.constructTree method to return the created tree when called with the specified numbers
        when(treeService.constructTree(new int[]{50, 30, 70, 20, 40, 60, 80})).thenReturn(tree);

        // Perform a POST request to the /process-numbers endpoint with the numbers as a parameter
        mockMvc.perform(post("/process-numbers")
                        .param("numbers", numbers))
                // Expect the status to be OK (200)
                .andExpect(status().isOk())
                // Expect the content type to be compatible with application/json
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public void testShowPrevious() throws Exception {
        // Create a new BinarySearchTree object
        BinarySearchTree tree = new BinarySearchTree();

        // Mock the treeService.getAllTrees method to return a list containing the created tree
        when(treeService.getAllTrees()).thenReturn(Collections.singletonList(tree));

        // Perform a GET request to the /previous-trees endpoint
        mockMvc.perform(get("/previous-trees"))
                // Expect the status to be OK (200)
                .andExpect(status().isOk())
                // Expect the content type to be compatible with text/plain
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                // Expect the response content to contain the string "Previous Trees"
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Previous Trees")));
    }
}