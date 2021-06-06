package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class MinHeapImpl<E extends Comparable> extends MinHeap<E> {

    public MinHeapImpl(){
        this.elements = (E[]) new Comparable[2];
        this.count = 0;
        this.elementsToArrayIndex = new HashMap<E, Integer>();

    }

    public void reHeapify(E element) {
        int i = getArrayIndex(element);
        downHeap(i);
        int j = getArrayIndex(element);
        upHeap(j);


    }

    protected int getArrayIndex(E element) {
        int index = elementsToArrayIndex.get(element);
        return  index;
    }

    protected void doubleArraySize() {
        E[] temp = (E[]) new Comparable[2 * elements.length];
        for (int i = 0; i < elements.length; i++){
            temp[i] = elements[i];
        }
        elements = temp;

    }

    @Override
    protected boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    protected boolean isGreater(int i, int j) {
        return super.isGreater(i, j);
    }

    @Override
    protected void swap(int i, int j) {
        E temp = this.elements[i];
        this.elements[i] = this.elements[j];
        elementsToArrayIndex.put(this.elements[i], i);
        this.elements[j] = temp;
        elementsToArrayIndex.put(this.elements[j], j);
    }

    @Override
    protected void upHeap(int k) {
        super.upHeap(k);
    }

    @Override
    protected void downHeap(int k) {
        super.downHeap(k);
    }

    @Override
    public void insert(E x) {
        // double size of array if necessary
        if (this.count >= this.elements.length - 1)
        {
            this.doubleArraySize();
        }
        //add x to the bottom of the heap
        this.elements[++this.count] = x;
        elementsToArrayIndex.put(x, this.count);
        //percolate it up to maintain heap order property
        this.upHeap(this.count);
    }

    @Override
    public E removeMin() {
        if (isEmpty())
        {
            throw new NoSuchElementException("Heap is empty");
        }
        E min = this.elements[1];
        this.swap(1, this.count--);
        this.downHeap(1);
        this.elements[this.count + 1] = null; //null it to prepare for GC
        elementsToArrayIndex.remove(min);

        return min;
    }
}

