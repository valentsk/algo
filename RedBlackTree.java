import java.util.*;
import java.util.LinkedList;
import java.util.Queue;


public enum Color {
    Black("black"), Red("gules");

    private String color;

    private Color(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
public class Node {
    public int key;
    public Color color;
    public Node left;
    public Node right;
    public Node parent;

    public Node() {
    }

    public Node(Color color) {
        this.color = color;
    }

    public Node(int key) {
        this.key = key;
        this.color = Color.Red;
    }

    /**
     * Find the height of a node in a tree species
     * 
     * @return
     */
    public int height() {
        return Math.max(left != null ? left.height() : 0, right != null ? right.height() : 0) + 1;
    }

    /**
     * Find the smallest node in the tree with this node as the root node
     * 
     * @return
     */
    public Node minimum() {
        Node pointer = this;
        while (pointer.left != RedBlackTree.NULL)
            pointer = pointer.left;
        return pointer;
    }

    @Override
    public String toString() {
        String position = "null";
        if (this.parent != RedBlackTree.NULL)
            position = this.parent.left == this ? "left" : "right";
        return "[key: " + key + ", color: " + color + ", parent: " + parent.key + ", position: " + position + "]";
    }
}


public class RedBlackTree {
    public final static Node NULL = new Node(Color.Black);

    public Node root;

    public RedBlackTree() {
        this.root = NULL;
    }

    /**
     * Left Rotation
     * 
     * @param node
     */
    public void leftRotate(Node node) {
        Node rightNode = node.right;

        node.right = rightNode.left;
        if (rightNode.left != RedBlackTree.NULL)
            rightNode.left.parent = node;

        rightNode.parent = node.parent;
        if (node.parent == RedBlackTree.NULL)
            this.root = rightNode;
        else if (node.parent.left == node)
            node.parent.left = rightNode;
        else
            node.parent.right = rightNode;

        rightNode.left = node;
        node.parent = rightNode;
    }

    /**
     * Right Rotation
     * 
     * @param node
     */
    public void rightRotate(Node node) {
        Node leftNode = node.left;

        node.left = leftNode.right;
        if (leftNode.right != RedBlackTree.NULL)
            leftNode.right.parent = node;

        leftNode.parent = node.parent;
        if (node.parent == RedBlackTree.NULL) {
            this.root = leftNode;
        } else if (node.parent.left == node) {
            node.parent.left = leftNode;
        } else {
            node.parent.right = leftNode;
        }

        leftNode.right = node;
        node.parent = leftNode;
    }

    public void insert(Node node) {
        Node parentPointer = RedBlackTree.NULL;
        Node pointer = this.root;

        while (pointer != RedBlackTree.NULL) {
            parentPointer = pointer;
            pointer = node.key < pointer.key ? pointer.left : pointer.right;
        }

        node.parent = parentPointer;
        if (parentPointer == RedBlackTree.NULL) {
            this.root = node;
        } else if (node.key < parentPointer.key) {
            parentPointer.left = node;
        } else {
            parentPointer.right = node;
        }

        node.left = RedBlackTree.NULL;
        node.right = RedBlackTree.NULL;
        node.color = Color.Red;
        this.insertFixUp(node);
    }

    private void insertFixUp(Node node) {
        // When the node is not the root node and the parent node of the node is red
        while (node.parent.color == Color.Red) {
            // The solution varies depending on whether the node's parent is a left or right child node
            if (node.parent == node.parent.parent.left) {
                Node uncleNode = node.parent.parent.right;
                if (uncleNode.color == Color.Red) { // If uncle node is red, parent must be black
                    // By turning the parent and sibling nodes red, the parent and sibling nodes black, and then determining if the color of the parent and parent is appropriate
                    uncleNode.color = Color.Black;
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    node = node.parent.parent;
                } else if (node == node.parent.right) { // Node is the right child of its parent node and uncle node is black
                    // Rotate the parent node of a node left
                    node = node.parent;
                    this.leftRotate(node);
                } else { // Node If uncle node is black, node is the left child of its parent node, and parent node is black
                    // Make the parent black, the parent red, rotate the parent right
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    this.rightRotate(node.parent.parent);
                }
            } else {
                Node uncleNode = node.parent.parent.left;
                if (uncleNode.color == Color.Red) {
                    uncleNode.color = Color.Black;
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    node = node.parent.parent;
                } else if (node == node.parent.left) {
                    node = node.parent;
                    this.rightRotate(node);
                } else {
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    this.leftRotate(node.parent.parent);
                }
            }
        }
        // If there were no nodes in the previous tree, the newly added points would be new nodes, and the newly inserted nodes would be red, so they need to be modified.
        this.root.color = Color.Black;
    }

    /**
     * n2 Replace n1
     * 
     * @param n1
     * @param n2
     */
    private void transplant(Node n1, Node n2) {

        if (n1.parent == RedBlackTree.NULL) { // If n1 is the root node
            this.root = n2;
        } else if (n1.parent.left == n1) { // If n1 is the left child of its parent
            n1.parent.left = n2;
        } else { // If n1 is the right child of its parent
            n1.parent.right = n2;
        }
        n2.parent = n1.parent;
    }

    
    private void innerWalk(Node node) {
        if (node != NULL) {
            innerWalk(node.left);
            System.out.println(node);
            innerWalk(node.right);
        }
    }

    /**
     * Intermediate traversal
     */
    public void innerWalk() {
        this.innerWalk(this.root);
    }

    /**
     * level traversal
     */
    public void print() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.println(temp);
            if (temp.left != NULL)
                queue.add(temp.left);
            if (temp.right != NULL)
                queue.add(temp.right);
        }
    }

    // lookup
    public Node search(int key) {
        Node pointer = this.root;
        while (pointer != NULL && pointer.key != key) {
            pointer = pointer.key < key ? pointer.right : pointer.left;
        }
        return pointer;
    }

}
