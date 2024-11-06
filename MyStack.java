/**
 * Zerlyne Nandwani-Simons
 * Oct. 23, 2024
 * CMSC-256-001
 *
 * Project 5 - Tag Matching
 * The purpose of this program is to implement a link node Stack to correctly pair tags.
 */

package cmsc256;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Scanner;

public class MyStack<E> implements StackInterface<E> {
    // reference to the top of the stack
    private Node<E> top;

    // inner private class
    private class Node<E> {
        private E data; // Entry in stack
        private Node next; // Link to nexE node

        private Node(E dataPortion)
        {
            this(dataPortion, null);
        }

        private Node(E dataPortion, Node linkPortion)
        {
            data = dataPortion;
            next = linkPortion;
        }

        private E getData()
        {
            return data;
        }

        private void setData(E newData)
        {
            data = newData;
        }

        private Node getNextNode()
        {
            return next;
        }

        private void setNextNode(Node nextNode)
        {
            next = nextNode;
        }
    }


    // implement methods from StackInterface
    @Override
    public void push(E newEntry) {
        Node<E> newNode = new Node<>(newEntry);

        if (newEntry == null) {
            throw new IllegalArgumentException("Null cannot be pushed onto stack.");
        }

        newNode.setNextNode(top);
        top = newNode;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        E temp = top.getData();
        top = top.getNextNode();
        return temp;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    @Override
    public boolean isEmpty() {
        if (top == null) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        top = null;
    }

    // static method
        // detects whether a html file is tag balanced
    public static boolean isBalanced(File webpage) throws FileNotFoundException {
        if (!webpage.exists()) {
            throw new IllegalArgumentException("Cannot open file.");
        }

        Scanner in = new Scanner(webpage);
        MyStack<String> stack = new MyStack<>();
        String line;
        boolean isBalanced = true;
     //   String word = in.next().trim();

        // read file line by line
        while (in.hasNextLine()) {
            line = in.nextLine();

            for (int i = 0; i < line.length(); i++) {
                // Open Tags
                if (line.charAt(i) == '<' && line.charAt(i + 1) != '/') {
                    for (int j = i + 1; j < line.length(); j++) {
                        if (line.charAt(j) == '>') {
                            String sub = line.substring(i + 1, j);
                            sub = sub.trim();

                            if (sub.length() > 0) {
                                System.out.println(sub);
                                stack.push(sub);

                            }
                           // i = j + 1;
                        }

                    }
                }
                // Close Tags
                if (line.charAt(i) == '<' && line.charAt(i + 1) == '/') {
                    for (int j = i + 2; j < line.length(); j++) {
                        if (line.charAt(j) == '>') {
                            String sub = line.substring(i + 2, j);
                            sub = sub.trim();

                            if (stack.isEmpty()) {
                                System.out.println("Empty stack pop");
                                return false;
                            }
                            else {
                                String popped = stack.pop();
                                if (!popped.equals(sub)) {
                                    System.out.println("pop dont match");
                                    return false;
                                }
                            }
                           // i = j + 1;

                        }
                    }
                }
            }



        }
        if (!stack.isEmpty()) {
            System.out.println("Empty stack");
            return false;
        }

        in.close();
        return true;
    }
}
