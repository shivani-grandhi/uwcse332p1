package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    E [] array;
    private int size;
    public ArrayStack() {
        this.array = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public void add(E work) {
        if (this.size == this.array.length) {
            int newLength = array.length * 2;
            E[] newArray = (E[]) new Object[newLength];
            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[size] = work;
        this.size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return array[size -1];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E nextElement = array[size - 1];
        size--;
        return nextElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
    }
}
