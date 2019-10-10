package com.xyzcorp.demos.generics;

import com.xyzcorp.demos.generics.subclassing.MySafeNode;
import com.xyzcorp.demos.generics.subclassing.Node;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SubclassTest {

    /**
     * 23. Subclass Test
     * Node<Integer> is the assigned variable. On the right hand
     * side is the MySafeNode which merely "passes the generic along"
     */
    @Test
    public void testIssueWithSubclassingGenerics() {
        Node<Integer> mySafeNode = new MySafeNode<>(40);
        assertThatThrownBy(() -> mySafeNode.setData(null)).isInstanceOf(NullPointerException.class);
    }
}
