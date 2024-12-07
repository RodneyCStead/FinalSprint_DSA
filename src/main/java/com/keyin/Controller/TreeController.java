package com.keyin.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.Service.TreeService;
import com.keyin.Tree.BinarySearchTree;
import com.keyin.Tree.TreeNode;
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
    public String showPrevious() {
        List<BinarySearchTree> trees = treeService.getAllTrees();
        if (trees.isEmpty()) {
            return "No trees available";
        }
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head><title>Previous Trees</title></head>")
                .append("<body>")
                .append("<h1>Previous Trees</h1>")
                .append("<form action='/enter-numbers' method='get'>")
                .append("<button type='submit'>Go Home</button>")
                .append("</form>")
                .append("<form action='/simple-view' method='get'>")
                .append("<button type='submit'>Simple View</button>")
                .append("</form>")
                .append("<pre>")
                .append(treesToJson(trees))
                .append("</pre>")
                .append("</body>")
                .append("</html>");
        return html.toString();
    }

    private String treesToJson(List<BinarySearchTree> trees) {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder json = new StringBuilder();
        for (BinarySearchTree tree : trees) {
            try {
                json.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree)).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

    @GetMapping("/simple-view")
    @ResponseBody
    public String simpleView() {
        List<BinarySearchTree> trees = treeService.getAllTrees();
        if (trees.isEmpty()) {
            return "No trees available";
        }
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head><title>Tree View</title></head>")
                .append("<body>")
                .append("<h1>Simple View</h1>")
                .append("<h3>This view will show the previous trees in a diagram form. The right route is displayed on top and the left route is on bottom.</h3>")
                .append("<h3>It is displayed this way so that the larger number (right value) is displayed on top of the lesser number (left value).</h3>")
                .append("<form action='/enter-numbers' method='get'>")
                .append("<button type='submit'>Go Home</button>")
                .append("</form>")
                .append("<form action='/previous-trees' method='get'>")
                .append("<button type='submit'>Show JSON View</button>")
                .append("</form>");
        for (BinarySearchTree tree : trees) {
            html.append(generateTreeHtml(tree));
        }
        html.append("</body>")
                .append("</html>");
        return html.toString();
    }

    private String generateTreeHtml(BinarySearchTree tree) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Tree ID: <b>").append(tree.getId()).append("</b></h1>")
                .append("<pre>")
                .append(generateTreeDiagram(tree.getRoot(), "", true))
                .append("</pre>");
        return html.toString();
    }

    // had alot of fun learning about this, I wanted to make a simple-view and diagram as follows:
    private String generateTreeDiagram(TreeNode node, String prefix, boolean isTail) {
        if (node == null) {
            return "";
        }
        StringBuilder diagram = new StringBuilder();
        diagram.append(prefix).append(isTail ? "└── " : "├── ").append(node.getValue()).append("\n");
        if (node.getRight() != null) {
            diagram.append(generateTreeDiagram(node.getRight(), prefix + (isTail ? "    " : "│   "), false));
        }
        if (node.getLeft() != null) {
            diagram.append(generateTreeDiagram(node.getLeft(), prefix + (isTail ? "    " : "│   "), true));
        }
        return diagram.toString();
    }
}