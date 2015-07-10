package com.zealcore.se.ui.internal;

import java.util.Collection;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.ui.util.MultiHashMap;

public class MultiHashMapTest {

    private static final int TWO = 2;

    private static final int NINE = 9;

    private static final int ELEVEN = 11;

    private static final int TEN = 10;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public final void testContainsKey() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);

        Assert.assertTrue(subject.containsKey(MultiHashMapTest.TEN));
        Assert.assertFalse(subject.containsKey(MultiHashMapTest.ELEVEN));
    }

    @Test
    public final void testContainsValue() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        Assert.assertTrue(subject.containsValue(MultiHashMapTest.TEN));
        Assert.assertTrue(subject.containsValue(MultiHashMapTest.ELEVEN));
        Assert.assertFalse(subject.containsValue(MultiHashMapTest.NINE));
    }

    @Test
    public final void testEntrySet() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        for (final Entry<Integer, Collection<Integer>> entry : subject
                .entrySet()) {
            Assert.assertNotNull(entry);
        }

        Assert.assertNotNull(subject.entrySet());

    }

    @Test
    public final void testGet() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        final Collection<Integer> name = subject.get(MultiHashMapTest.TEN);
        Assert.assertNotNull(name);
        Assert.assertTrue(name.contains(MultiHashMapTest.TEN));
        Assert.assertTrue(name.contains(MultiHashMapTest.ELEVEN));
        Assert.assertEquals(MultiHashMapTest.TWO, name.size());

    }

    @Test
    public final void testIsEmpty() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        Assert.assertFalse(subject.isEmpty());

        subject.removeAll(MultiHashMapTest.TEN);
        subject.remove(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        Assert.assertTrue(subject.isEmpty());
    }

    @Test
    public final void testKeySet() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        final Set<Integer> name = subject.keySet();
        Assert.assertTrue(name.contains(MultiHashMapTest.TEN));
        Assert.assertTrue(name.contains(MultiHashMapTest.NINE));

        Assert.assertEquals(MultiHashMapTest.TWO, name.size());

    }

    @Test
    public final void testPut() {

        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        final Collection<Integer> collection = subject
                .get(MultiHashMapTest.TEN);
        Assert.assertEquals(MultiHashMapTest.ELEVEN, collection.iterator()
                .next().intValue());

    }

    @Test
    public final void testRemoveAll() {

        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        subject.removeAll(MultiHashMapTest.TEN);

        Assert.assertFalse(subject.isEmpty());
        Assert.assertTrue(subject.get(MultiHashMapTest.TEN).isEmpty());

    }

    @Test
    public final void testRemoveV() {

        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        Assert.assertEquals(MultiHashMapTest.TWO + 1, subject.size());
        subject.remove(MultiHashMapTest.ELEVEN);
        // Removed the value ELEVEN from all keys
        Assert.assertEquals(1, subject.size());
    }

    @Test
    public final void testRemoveKV() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        Assert.assertEquals(MultiHashMapTest.TWO + 1, subject.size());
        subject.remove(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);
        // Removed the value ELEVEN from only the NINE key
        Assert.assertEquals(MultiHashMapTest.TWO, subject.size());
    }

    @Test
    public final void testSize() {

        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        int size = 0;
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        Assert.assertEquals(++size, subject.size());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        Assert.assertEquals(++size, subject.size());
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);
        Assert.assertEquals(++size, subject.size());

    }

    @Test
    public final void testValues() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        final Collection<Integer> name = subject.values();

        Assert.assertEquals(MultiHashMapTest.TWO + 1, name.size());
        Assert.assertTrue(name.contains(MultiHashMapTest.TEN));
        Assert.assertTrue(name.contains(MultiHashMapTest.ELEVEN));
    }

    @Test
    public final void testNewInstance() {
        Assert.assertNotNull(MultiHashMap.newInstance());
    }

    @Test
    public final void testClear() {
        final MultiHashMap<Integer, Integer> subject = MultiHashMap
                .newInstance();

        Assert.assertTrue(subject.isEmpty());
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.TEN);
        subject.put(MultiHashMapTest.TEN, MultiHashMapTest.ELEVEN);
        subject.put(MultiHashMapTest.NINE, MultiHashMapTest.ELEVEN);

        final Collection<Integer> name = subject.values();

        Assert.assertEquals(MultiHashMapTest.TWO + 1, name.size());
        subject.clear();
        final boolean empty = subject.isEmpty();
        Assert.assertTrue(empty);

    }

}
