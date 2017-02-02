package com.fuscard.system_x.linearAlgebra.matchers;

import java.util.HashMap;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class HashMapWithSameElements<U, V> extends TypeSafeMatcher<HashMap<U, V>> {

    private final HashMap<U, V> expected;

    public HashMapWithSameElements(HashMap<U, V> expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(HashMap<U, V> item) {
        if (expected == null || item == null) {
            return false;
        }
        if (!(expected.keySet().containsAll(item.keySet())
                && item.keySet().containsAll(expected.keySet()))) {
            return false;
        }
        for(U u : item.keySet()){
            if(item.get(u) != expected.get(u)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with same elements as");
    }

    public static <R,S> HashMapWithSameElements<R,S> aHashMapWithSameElementsAs(HashMap<R,S> map) {
        return new HashMapWithSameElements<>(map);
    }
}
