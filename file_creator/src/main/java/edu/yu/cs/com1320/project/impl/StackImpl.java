package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Stack;

public class StackImpl<T> implements Stack<T> {
    private int count;
    private DataNode<T> top;

    private class DataNode<T>{
        private T value;
        private DataNode<T> next;

        private DataNode(T value){
            this.value = value;
            this.next = null;
        }


    }


    public StackImpl(){
        count = 0;
        top = null;
    }

    public void push(T element) {
        if ( element == null){
            throw new IllegalArgumentException();
        }
        DataNode<T> point = new DataNode<>(element);
        point.next = top;
        top = point;
        count++;
    }

    public T pop() {
        if (count == 0) {
            return null;
        }
        DataNode<T> temp = top;
        top = top.next;
        count--;
        return (T) temp.value;
    }

    public T peek() {
        if (count == 0){
            return null;
        }
        return (T) top.value;
    }

    public int size() {
        return count;
    }
}

