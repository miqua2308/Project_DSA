package Stack;

import java.util.EmptyStackException;

public class Stack<T> {
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node top;

    public void push(T data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }

    public T pop() {
        if (top == null) throw new EmptyStackException();
        T data = top.data;
        top = top.next;
        return data;
    }

    public T peek() {
        if (top == null) throw new EmptyStackException();
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
