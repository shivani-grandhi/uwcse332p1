package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    private int front;
    private int end;
    private int size;
    private E[] array;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.front = 0;
        this.end = 0;
        this.size = 0;
        this.array = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if(isFull()){
            throw new IllegalStateException();
        }
        array[end] = work;
        end = (end + 1) % array.length;
        size++;
        if(front == -1) {
            front = end;
        }
    }

    @Override
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return array[(front)];
    }

    @Override
    public E peek(int i) {

        return array[(front + i) % array.length];
    }

    @Override
    public E next() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        E first = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        return first;
    }

    @Override
    public void update(int i, E value) {
        if( 0 <= i && i < size()) {
            array[front + i] = value;
        } else {
            throw new IndexOutOfBoundsException();
        }
        if(!this.hasWork()) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public void clear() {
        front = end = size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
