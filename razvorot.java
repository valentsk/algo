package homework.algo;

public class razvorot {
    Node head;
    Node tail;

    public void revert(){
        Node currentNode = head;
        while (currentNode != null) {
            Node next = currentNode.next;
            Node previous = currentNode.previous;
            currentNode.next = previous;
            currentNode.previous = next;
            if (previous == null) {
                tail = currentNode;
            }
            if (next == null) {
                head = currentNode;
            }
            currentNode = next;
        }
    }

    public class Node{
        int value;
        Node next;
        Node previous;
    }
}
