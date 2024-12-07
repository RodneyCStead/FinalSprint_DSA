package com.keyin.Repository;

import com.keyin.Tree.BinarySearchTree;
import com.keyin.Tree.TreeNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class TreeRepositoryTest {

    @Autowired
    private TreeRepository treeRepository;

    @Test
    public void testDatabaseConnection() {

        // Create a sample BinarySearchTree object
        BinarySearchTree tree = new BinarySearchTree();
        TreeNode root = new TreeNode(50);
        tree.setRoot(root);

        // Save the tree to the database
        BinarySearchTree savedTree = treeRepository.save(tree);

        // Verify that the tree was saved successfully
        assertThat(savedTree).isNotNull();
        assertThat(savedTree.getId()).isNotNull();
        assertThat(savedTree.getRoot().getValue()).isEqualTo(50);
    }
}