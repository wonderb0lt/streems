package com.github.javachat.streems.spliterators;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A spliterator for breadth-first traversal
 *
 * @param <T> type of the nodes in the tree
 */
public final class BreadthFirstSpliterator<T>
    extends NaryTreeSpliterator<T>
{
    private final Deque<Iterator<T>> deque = new ArrayDeque<>();

    public BreadthFirstSpliterator(final T root,
        final Function<T, Iterator<T>> fn)
    {
        super(root, fn);
        deque.addFirst(Collections.singletonList(root).iterator());
    }

    @Override
    public boolean tryAdvance(final Consumer<? super T> action)
    {
        if (deque.isEmpty())
            return false;
        final Iterator<T> iterator = deque.removeFirst();
        iterator.forEachRemaining(elem -> processElement(elem, action));
        return true;
    }

    private void processElement(final T element, Consumer<? super T> action) {
        deque.add(fn.apply(element));
        action.accept(element);
    }
}
