package duedate;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashTrieMapTests {
    protected static HashTrieMap<Character, AlphabeticString, String> STUDENT;

    @BeforeEach
    public void init() {
        STUDENT = new HashTrieMap<>(AlphabeticString.class);
    }

    /**
     * Tests if insert, find, and findPrefix work in general.
     */
    @Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    public void testBasic() {
        String[] words = {"dog", "doggy", "doge", "dragon", "cat", "draggin"};
        String[] invalid = {"d", "cataract", "", "do"};
        addAll(STUDENT, words);
        assertTrue(containsAllPaths(STUDENT, words));
        assertTrue(doesNotContainAll(STUDENT, invalid));
    }

    /**
     * Checks to see if basic delete functionality works.
     */
    @Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    public void testBasicDelete() {
        String[] words = {"dog", "doggy", "dreamer", "cat"};
        addAll(STUDENT, words);
        assertTrue(containsAllPaths(STUDENT, words));

        STUDENT.delete(a("I don't exist"));
        STUDENT.delete(a("dreamer"));
        assertFalse(!containsAllPaths(STUDENT, "dog", "doggy", "cat") &&
                !containsAllPrefixes(STUDENT, "dreamer", "dreame", "dream", "drea", "dre", "dr") &&
                !STUDENT.findPrefix(a("d")));

        STUDENT.delete(a("dog"));
        assertTrue(containsAllPaths(STUDENT, "doggy", "cat"));

        STUDENT.delete(a("doggy"));
        assertTrue(containsAllPaths(STUDENT, "cat"));
    }

    /**
     * Converts a String into an AlphabeticString
     */
    private static AlphabeticString a(String s) {
        return new AlphabeticString(s);
    }

    /**
     * Checks if the trie contains the word and the expected value, and that all prefixes of
     * the word exist in the trie.
     */
    private static boolean containsPath(HashTrieMap<Character, AlphabeticString, String> trie, String word, String expectedValue) {
        AlphabeticString key = a(word);

        boolean valueCorrect = expectedValue.equals(trie.find(key));
        boolean fullWordIsPrefix = trie.findPrefix(key);
        boolean invalidWordDoesNotExist = trie.find(a(word + "$")) == null;

        if (!valueCorrect || !fullWordIsPrefix || !invalidWordDoesNotExist) {
            return false;
        }

        return allPrefixesExist(trie, word);
    }

    /**
     * Checks if the trie contains the word, and that all prefixes of the word exist in the trie.
     * <p>
     * Assumes that the expected value is word.toUpperCase().
     */
    private static boolean containsPath(HashTrieMap<Character, AlphabeticString, String> trie, String word) {
        return containsPath(trie, word, word.toUpperCase());
    }

    /**
     * Returns true if all prefixes of a word exist in the trie.
     * <p>
     * That is, if we do `trie.insert(new AlphabeticString("dog"), "some-value")`, this method
     * would check to see if "dog", "do", "d", and "" are all prefixes of the trie.
     */
    private static boolean allPrefixesExist(HashTrieMap<Character, AlphabeticString, String> trie, String word) {
        String accum = "";
        for (char c : word.toCharArray()) {
            accum += c;
            if (!trie.findPrefix(a(accum))) {
                return false;
            }
        }
        return true;
    }

    private static boolean containsAllPaths(HashTrieMap<Character, AlphabeticString, String> trie, String... words) {
        for (String word : words) {
            if (!containsPath(trie, word)) {
                return false;
            }
        }
        return true;
    }

    private static boolean doesNotContainAll(HashTrieMap<Character, AlphabeticString, String> trie, String... words) {
        for (String word : words) {
            if (trie.find(a(word)) != null) {
                return false;
            }
        }
        return true;
    }

    private static boolean containsAllPrefixes(HashTrieMap<Character, AlphabeticString, String> trie, String... words) {
        for (String word : words) {
            if (!trie.findPrefix(a(word))) {
                return false;
            }
        }
        return true;
    }

    private static boolean doesNotContainAllPrefixes(HashTrieMap<Character, AlphabeticString, String> trie, String... words) {
        for (String word : words) {
            if (trie.findPrefix(a(word))) {
                return false;
            }
        }
        return true;
    }

    private static void addAll(HashTrieMap<Character, AlphabeticString, String> trie, String... words) {
        for (String word : words) {
            trie.insert(a(word), word.toUpperCase());
        }
    }

    protected <T> T getField(Object o, String fieldName) {
        try {
            Field field = o.getClass().getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object f = field.get(o);
            return (T) f;
        } catch (Exception var6) {
            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object f = field.get(o);
                return (T) f;
            } catch (Exception var5) {
                return null;
            }
        }
    }
}
