package com.keyin.Controller;

import com.keyin.Service.TreeService;
import com.keyin.Tree.BinarySearchTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TreeController {

    @Autowired
    private TreeService treeService;

    @GetMapping("/enter-numbers")
    @ResponseBody
    public String enterNumbers() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>Enter Numbers</title></head>" +
                "<body>" +
                "<form action='/process-numbers' method='post'>" +
                "<label for='numbers'>Enter numbers (comma-separated):</label>" +
                "<input type='text' id='numbers' name='numbers'>" +
                "<button type='submit'>Submit</button>" +
                "</form>" +
                "<form action='/previous-trees' method='get'>" +
                "<button type='submit'>Show Previous</button>" +
                "</form>" +
                "</body>" +
                "</html>";
    }

    @PostMapping("/process-numbers")
    @ResponseBody
    public BinarySearchTree processNumbers(@RequestParam("numbers") String numbers) {
        String[] numberStrings = numbers.split(",");
        int[] numberArray = new int[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            numberArray[i] = Integer.parseInt(numberStrings[i].trim());
        }

        BinarySearchTree tree = treeService.constructTree(numberArray);
        treeService.saveTree(tree);

        return tree;
    }

    @GetMapping("/previous-trees")
    @ResponseBody
    public List<BinarySearchTree> showPrevious() {
        return treeService.getAllTrees();
    }
}