package com.keyin.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.Service.TreeService;
import com.keyin.Tree.BinarySearchTree;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TreeController {

    private final TreeService treeService;
    private final ObjectMapper objectMapper;

    public TreeController(TreeService treeService, ObjectMapper objectMapper) {
        this.treeService = treeService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/enter-numbers")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> enterNumbers() {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/index.html")).build();
    }

    @PostMapping("/process-numbers")
    @CrossOrigin(origins = "*")
    public ModelAndView processNumbersPost(@RequestParam("numbers") String numbers) throws JsonProcessingException {
        String[] numberStrings = numbers.split(",");
        int[] numberArray = new int[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            numberArray[i] = Integer.parseInt(numberStrings[i].trim());
        }

        BinarySearchTree tree = treeService.constructTree(numberArray);
        treeService.saveTree(tree);

        String treeJson = objectMapper.writeValueAsString(tree);

        ModelAndView modelAndView = new ModelAndView("process-numbers");
        modelAndView.addObject("numbers", treeJson);
        return modelAndView;
    }

    @GetMapping("/process-numbers")
    @CrossOrigin(origins = "*")
    public ModelAndView processNumbersGet(@RequestParam("numbers") String numbers) throws JsonProcessingException {
        String[] numberStrings = numbers.split(",");
        int[] numberArray = new int[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            numberArray[i] = Integer.parseInt(numberStrings[i].trim());
        }

        BinarySearchTree tree = treeService.constructTree(numberArray);
        treeService.saveTree(tree);

        String treeJson = objectMapper.writeValueAsString(tree);

        ModelAndView modelAndView = new ModelAndView("process-numbers-view");
        modelAndView.addObject("numbers", treeJson);
        return modelAndView;
    }

    @GetMapping("/previous-trees")
    @CrossOrigin(origins = "*")
    public List<BinarySearchTree> getPreviousTrees() {
        return treeService.getAllTrees();
    }

    @GetMapping("/previous-trees-view")
    @CrossOrigin(origins = "*")
    public ModelAndView previousTreesView() {
        return new ModelAndView("previous-trees-view");
    }

    @GetMapping("/simple-view")
    @CrossOrigin(origins = "*")
    public ModelAndView simpleView() throws JsonProcessingException {
        List<BinarySearchTree> trees = treeService.getAllTrees();
        String treesJson = objectMapper.writeValueAsString(trees);

        ModelAndView modelAndView = new ModelAndView("simple-view-view");
        modelAndView.addObject("treesJson", treesJson);
        return modelAndView;
    }
}