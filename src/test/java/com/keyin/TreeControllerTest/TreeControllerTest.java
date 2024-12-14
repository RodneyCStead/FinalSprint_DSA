package com.keyin.TreeControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.Controller.TreeController;
import com.keyin.Service.TreeService;
import com.keyin.Tree.BinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TreeControllerTest {

    @Mock
    private TreeService treeService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TreeController treeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnterNumbers() {
        ResponseEntity<Void> response = treeController.enterNumbers();
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(URI.create("/index.html"), response.getHeaders().getLocation());
    }

    @Test
    public void testProcessNumbersPost() throws JsonProcessingException {
        String numbers = "1,2,3";
        int[] numberArray = {1, 2, 3};
        BinarySearchTree tree = new BinarySearchTree();
        when(treeService.constructTree(numberArray)).thenReturn(tree);
        when(objectMapper.writeValueAsString(tree)).thenReturn("treeJson");

        ModelAndView modelAndView = treeController.processNumbersPost(numbers);

        assertEquals("process-numbers", modelAndView.getViewName());
        assertEquals("treeJson", modelAndView.getModel().get("numbers"));
        verify(treeService, times(1)).constructTree(numberArray);
        verify(treeService, times(1)).saveTree(tree);
    }

    @Test
    public void testSimpleView() throws JsonProcessingException {
        List<BinarySearchTree> trees = Collections.singletonList(new BinarySearchTree());
        when(treeService.getAllTrees()).thenReturn(trees);
        when(objectMapper.writeValueAsString(trees)).thenReturn("treesJson");

        ModelAndView modelAndView = treeController.simpleView();

        assertEquals("simple-view-view", modelAndView.getViewName());
        assertEquals("treesJson", modelAndView.getModel().get("treesJson"));
        verify(treeService, times(1)).getAllTrees();
    }
}