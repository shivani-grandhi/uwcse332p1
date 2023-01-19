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
        HashTrieNode temp = (HashTrieNode) this.root;
        HashTrieNode temp2 = (HashTrieNode) this.root;
        HashTrieNode multiChild = null;
        A holderChild = null;
        A lastChild = null;

        if (key == null) {
            throw new NoSuchElementException();
        } else {
            for (A findKey : key) {
                Object value = temp.pointers.get(findKey);
                if (value == null) {
                    return;
                } else {
                    if (temp.pointers.size() > 1 || temp.value != null) {
                        multiChild = temp;
                        holderChild = findKey;
                    }
                }
                temp = temp.pointers.get(findKey);
                lastChild = findKey;
            }
            if (temp.pointers.size() > 0) {
                temp.value = null;
            } else if (multiChild != null) {
                multiChild.pointers.remove(holderChild);

            }
            if (temp.value == null) {
                return;
            }
            temp.value = null;
            this.size--;
            if (temp2.pointers.size() <= 1) {
                this.size--;
                temp2.pointers.remove(lastChild);
            }
        }
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = new HashTrieNode();
    }
}
