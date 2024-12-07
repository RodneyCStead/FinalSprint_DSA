package com.keyin.Tree;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BinarySearchTree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TreeNode> nodes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TreeNode root;

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            TreeNode newNode = new TreeNode(value);
            nodes.add(newNode);
            return newNode;
        }
        if (value < root.getValue()) {
            root.setLeft(insertRec(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(insertRec(root.getRight(), value));
        }
        return root;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
}