package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private Node<E> head;
    private Node<E> end;
    private int size;

    //Linked list Node inner class
    static class Node<E> {
        E data;
        Node<E> next;
        public Node(E data) {
            this.data = data;
            next = null;
        }
    }
    public ListFIFOQueue(){
        size = 0;
    }

    @Override
    public void add(E work) {
        if(!hasWork()) {
            head = end = new Node(work);
        }
        else {
            end.next = new Node(work);
            end = end.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }
        return head.data;
    }

    @Override
    public E next() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }
        size --;
        Node<E> temp = head;
        E data = temp.data;
        head = head.next;
        temp = null;
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head.next = head;
        head = null;
        size = 0;
    }
}
