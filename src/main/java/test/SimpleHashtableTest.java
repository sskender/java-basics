package test;

import hashtable.SimpleHashtable;
import org.junit.Test;

public class SimpleHashtableTest {

    SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

    @Test
    public void testTableSizeFunction() {
        assert examMarks.calculateTableSize(2) == 2;
        assert examMarks.calculateTableSize(3) == 4;
        assert examMarks.calculateTableSize(4) == 4;
        assert examMarks.calculateTableSize(7) == 8;
        assert examMarks.calculateTableSize(33) == 64;
        assert examMarks.calculateTableSize(2000) == 2048;
        assert examMarks.calculateTableSize(4096) == 4096;
        assert examMarks.calculateTableSize(4097) == 8192;
    }

    @Test
    public void testEmpty() {
        assert examMarks.isEmpty();
    }

    @Test
    public void testEmptySize() {
        assert examMarks.size() == 0;
    }

    @Test
    public void testSizeAfterPut() {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        assert examMarks.size() == 4;
    }

    @Test
    public void testValueUpdate() {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        Integer ivanaGrade = examMarks.get("Ivana");
        assert ivanaGrade == 5;
    }

    @Test
    public void testValueUpdateSize() {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        assert examMarks.size() == 4;
    }

    @Test
    public void testContainsKey() {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 3);
        assert examMarks.containsKey("Ivana");
        assert examMarks.containsKey("Ante");
        assert !examMarks.containsKey("Jasna");
    }

    @Test
    public void testContainsValue() {
        examMarks.put("Ivana", 3);
        assert examMarks.containsValue(3);
        assert !examMarks.containsValue(2);
    }

    @Test
    public void testRemoveNull() {
        examMarks.remove("Jasna");
        assert !examMarks.containsKey("Jasna");
    }

    @Test
    public void testRemoveFirst() {
        examMarks.put("Jasna", 5);
        examMarks.remove("Jasna");
        assert examMarks.size() == 0;
        assert examMarks.get("Jasna") == null;
    }

    @Test
    public void testRemoveWhenOverflow1() {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        examMarks.remove("Kristina");
        assert !examMarks.containsKey("Kristina");
        assert examMarks.containsKey("Ivana");
        assert examMarks.size() == 3;
    }

    @Test
    public void testRemoveWhenOverflow2() {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        examMarks.remove("Ivana");
        assert !examMarks.containsKey("Ivana");
        assert examMarks.containsKey("Kristina");
        assert examMarks.size() == 3;
    }

    @Test
    public void testToString() {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);
        System.out.println(examMarks.toString());
        assert true;
    }

}
