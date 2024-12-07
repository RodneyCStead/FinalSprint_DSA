package com.keyin.Repository;

import com.keyin.Tree.BinarySearchTree;
import com.keyin.Tree.TreeNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeRepository extends CrudRepository <BinarySearchTree, Long> {
}
