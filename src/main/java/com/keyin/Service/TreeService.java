package com.keyin.Service;

import com.keyin.Repository.TreeRepository;
import com.keyin.Tree.BinarySearchTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeService {

    @Autowired
    private TreeRepository treeRepository;

    private List<BinarySearchTree> trees = new ArrayList<>();

    public BinarySearchTree constructTree(int[] numbers) {
        BinarySearchTree tree = new BinarySearchTree();
        for (int number : numbers) {
            tree.insert(number);
        }
        return tree;
    }

    public void saveTree(int[] numbers, BinarySearchTree tree) {
        treeRepository.save(tree);
    }

    public List<BinarySearchTree> getAllTrees() {
        return trees;
    }
}