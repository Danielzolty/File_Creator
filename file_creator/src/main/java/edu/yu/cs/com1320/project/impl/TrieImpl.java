package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Trie;

import java.util.*;

public class TrieImpl<Value> implements Trie<Value> {
    private static final int alphabetSize = 256; // extended ASCII
    private Node root; // root of trie
    private Value tester;

    private class Node<Value>
    {
        protected Set<Value> set = null;
        protected Node[] links = new Node[TrieImpl.alphabetSize];
    }

    public TrieImpl(){
        this.tester = null;
    }

    /**
     * add the given value at the given key
     * @param key
     * @param val
     */
    @Override
    public void put(String key, Value val) {
        //deleteAll the value from this key
        String replacedKey = key.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        if (val == null)
        {
            return;
        }
        else
        {
            this.root = put(this.root, replacedKey, val, 0);
        }
    }

    private Node put(Node x, String key, Value val, int d)
    {
        //create a new node
        if (x == null)
        {
            x = new Node();
        }
        //we've reached the last node in the key,
        //set the value for the key and return the node
        if (d == key.length())
        {
            if(x.set == null){
                x.set = new HashSet<>(0);
            }
            x.set.add(val);
            return x;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        x.links[c] = this.put(x.links[c], key, val, d + 1);
        return x;
    }

    /**
     * get all exact matches for the given key, sorted in descending order.
     * Search is CASE INSENSITIVE.
     * @param key
     * @return a List of matching Values, in descending order
     */
    @Override
    public List<Value> getAllSorted(String key, Comparator<Value> comparator) {
        if (key == null){
            return null;
        }
        String replacedKey = key.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        Node x = get(root, replacedKey, 0);
        if (x == null){
            return new ArrayList<Value>();
        }
        Set temp = x.set;
        if (temp == null){
            return new ArrayList<Value>();
        }
        List<Value> list = new ArrayList<>(temp);
        if (list.isEmpty()){
            return list;
        }
        list.sort(comparator);

        return list;
    }

    private Node get(Node x, String key, int d)
    {
        //link was null - return null, indicating a miss
        if (x == null)
        {
            return null;
        }
        //we've reached the last node in the key,
        //return the node
        if (d == key.length())
        {
            return x;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        return this.get(x.links[c], key, d + 1);
    }

    /**
     * get all matches which contain a String with the given prefix, sorted in descending order.
     * For example, if the key is "Too", you would return any value that contains "Tool", "Too", "Tooth", "Toodle", etc.
     * Search is CASE INSENSITIVE.
     * @param prefix
     * @return a List of all matching Values containing the given prefix, in descending order
     */
    @Override
    public List<Value> getAllWithPrefixSorted(String prefix, Comparator<Value> comparator) {
        if (prefix == null){
            return  null;
        }
        String replacedPrefix = prefix.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        Node x = get(root, replacedPrefix, 0);
        if (x == null){
            return new ArrayList<Value>();
        }
        List<Value> inputSet = new ArrayList<>();
        if (x.links != null){
            inputSet = prefixSortedList(inputSet, x, replacedPrefix);
        }

        Set temp = x.set;
        //System.out.println(temp);
        if (x.set != null){
            inputSet.addAll(x.set);
        }
        Set<Value> set = new HashSet<>(inputSet);
        List<Value> inputList = new ArrayList<>(set);

        if (inputList.isEmpty()){
            return inputList;
        }
        inputList.sort(comparator);
        return inputList;
    }

    private List<Value> prefixSortedList(List <Value> list, Node x, String st){
        for (int i = 0; i < x.links.length; i++){
            Node y = x.links[i];
            if (y == null){
                continue;
            }
            if (y != null){
                char z = (char) i;
                String newString = st + z;
                //System.out.println(newString);
                prefixSortedList(list, y, newString);
            }
            if (y.set != null) {
                list.addAll((y.set));
            }
        }

        return list;
    }



    /**
     * Delete all matches that contain a String with the given prefix.
     * Search is CASE INSENSITIVE.
     * @param prefix
     * @return a Set of all Values that were deleted.
     */
    @Override
    public Set<Value> deleteAllWithPrefix(String prefix) {
        if (prefix == null){ return new HashSet<>();}
        String replacedPrefix = prefix.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        Node x = get(root, replacedPrefix, 0);
        if (x == null){
            return new HashSet<>();
        }
        List<Value> inputSet = new ArrayList<>();
        if (x.links != null){
            inputSet = prefixSortedList(inputSet, x, replacedPrefix);
            if (x.set != null){
                inputSet.addAll(x.set);
            }
        } else {
            return new HashSet<>();
        }
        for (int i = 0; i < x.links.length; i++){
            x.links[i] = null;
        }
        deleteAll(root, replacedPrefix, 0);


        Set<Value> returnSet = new HashSet<>(inputSet);

        return returnSet;
    }

    /**
     * delete ALL exact matches for the given key
     * @param key
     * @return a Set of all Values that were deleted.
     */
    @Override
    public Set<Value> deleteAll(String key) {
        if (key == null){
            return new HashSet<>();
        }
        String replacedKey = key.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        Node x = get(this.root, replacedKey, 0);
        if (x == null){
            return new HashSet<Value>();
        }
        Set temp = x.set;
        this.root = deleteAll(this.root, replacedKey, 0);
        if (temp == null){
            return new HashSet<>();
        }
        return temp;
    }

    private Node deleteAll(Node x, String key, int d)
    {
        if (x == null)
        {
            return null;
        }
        //we're at the node to del - set the val to null
        if (d == key.length())
        {
            x.set = null;
        }
        //continue down the trie to the target node
        else
        {
            char c = key.charAt(d);
            x.links[c] = this.deleteAll(x.links[c], key, d + 1);
        }
        //this node has a val – do nothing, return the node
        if (x.set != null)
        {
            return x;
        }
        //remove subtrie rooted at x if it is completely empty
        for (int c = 0; c <TrieImpl.alphabetSize; c++)
        {
            if (x.links[c] != null)
            {
                return x; //not empty
            }
        }
        //empty - set this link to null in the parent
        return null;
    }



    /**
     * delete ONLY the given value from the given key. Leave all other values.
     * @param key
     * @param val
     * @return if there was a Value already at that key, return that previous Value. Otherwise, return null.
     */
    @Override
    public Value delete(String key, Value val) {
        if (key == null){ return null;}
        String replacedKey = key.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        if (val == null){
            return null;
        }
        Node y = get(this.root, replacedKey, 0);
        if (y == null){
            return null;
        }
        Set x = y.set;
        if (x == null){
            return null;
        }
        if (x.contains(val)){
            Value temp = val;
            x.remove(val);
            if (x.isEmpty()){
                deleteAll(this.root, replacedKey, 0);
            }
            return temp;
        }
        return null;
    }
}
