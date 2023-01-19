package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null){
            throw new IllegalArgumentException();
        }
        if (this.root == null) {
            this.root = new HashTrieNode();
        }
        HashTrieNode root = (HashTrieNode) this.root;
        for (A search : key){
            if (!root.pointers.containsKey(search)){
                root.pointers.put(search, new HashTrieNode());
            }
            root = root.pointers.get(search);
        }
        if(root.value == null){
            size++;
        };
        root.value = value;
        return root.value;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode root = (HashTrieNode) this.root;
        for (A temp : key){
            if (!root.pointers.containsKey(temp)){
                return null;
            }
            root = root.pointers.get(temp);
        }
        return root.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode node = (HashTrieNode) this.root;
        for (A search : key){
            if (!node.pointers.containsKey(search)){
                return false;
            }
            node = node.pointers.get(search);
            if (node == null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode temp = (HashTrieNode)this.root;

        Iterator<A> itr = key.iterator();
        A temp2 = null;

        HashTrieNode node = (HashTrieNode)this.root;

        while (itr.hasNext()) {
            A next = itr.next();

            if (node == null) {
                return;
            }

            if (node.value != null || node.pointers.size() >= 2) {
                temp = node;
                temp2 = next;
            }

            if (node.pointers.size() != 0) {
                node = node.pointers.get(next);
            } else {
                return;
            }
        }
        if (node != null && node.value != null) {
            if (node.pointers.size() != 0) {
                node.value = null;
            } else if (temp2 != null) {
                temp.pointers.remove(temp2);
            }
            node.value = null;

            this.size--;
        }
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = new HashTrieNode();
    }
}
