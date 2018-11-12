package hashtable;

/**
 * Simple hash table implementation.
 * <p>
 * Data structure is an array of slots, each slot contains (key, value) pair.
 * Slot is an index in that array.
 * Slot index is calculated by modulo of key hashcode.
 *
 * @param <K> key type
 * @param <V> value type
 * @author sskender
 */
public class SimpleHashtable<K, V> {


    private static final int DEFAULT_SIZE = 16;
    private TableEntry<K, V>[] table;
    private int size;


    /**
     * If custom size not specified, table of size DEFAULT_SIZE is be created.
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable() {
        this.table = (TableEntry<K, V>[]) new TableEntry[SimpleHashtable.DEFAULT_SIZE];
        this.size = 0;
    }


    /**
     * Create table of custom number of slots.
     *
     * @param numberOfSlots wanted table size
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int numberOfSlots) {
        this.table = (TableEntry<K, V>[]) new TableEntry[calculateTableSize(numberOfSlots)];
        this.size = 0;
    }


    /**
     * Size of the table will be integer with this characteristics:
     * exponent of 2
     * smallest number which is greater than @param x
     *
     * This supports only 32-bit values.
     *
     * Source:
     * https://stackoverflow.com/questions/364985/algorithm-for-finding-the-smallest-power-of-two-thats-greater-or-equal-to-a-giv
     * <p>
     * Example:
     * calculateTableSize(3) = 4
     * calculateTableSize(4) = 4
     * calculateTableSize(5) = 8
     *
     * @param x desired number of slots
     * @return calculated table size
     */
    public final int calculateTableSize(int x) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException("Size can not be less than zero");
        }

        x--;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;

        return x + 1;
    }


    /**
     * Calculate index of the slot in which key should be located via hash method.
     *
     * @param key key
     * @return slot index in table
     */
    private int getSlotIndex(K key) {
        return Math.abs(key.hashCode()) % this.table.length;
    }


    /**
     * Store (key, value) pair in table.
     * <p>
     * If key already exists in table then update its value,
     * do not add another entry with the same key.
     *
     * @param key   key
     * @param value value
     */
    public void put(K key, V value) {
        if (key == null) {
            return;
        }

        int slotIndex = getSlotIndex(key);

        if (this.table[slotIndex] == null) {
            /* no overflow */
            this.table[slotIndex] = new TableEntry<K, V>(key, value);
            this.size++;
        } else {
            /* overflow happened */
            TableEntry<K, V> temp = this.table[slotIndex];

            while (!temp.getKey().equals(key) && temp.next != null) {
                temp = temp.getNext();
            }

            if (temp.getKey().equals(key)) {
                temp.setValue(value);
            } else {
                temp.setNext(new TableEntry<K, V>(key, value));
                this.size++;
            }

        }
    }


    /**
     * Return (key, value) pair if key exists in table.
     * Returns null if:
     * key does not exist in table
     * key is null
     *
     * @param key key
     * @return value
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }

        int slotIndex = getSlotIndex(key);
        TableEntry<K, V> temp = this.table[slotIndex];

        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return temp.getValue();
            } else {
                temp = temp.getNext();
            }
        }

        return null;
    }


    /**
     * Return total number of (key, value) pairs stored in table
     *
     * @return table size
     */
    public int size() {
        return this.size;
    }


    /**
     * Return true if key is stored in table,
     * otherwise return false.
     *
     * @param key key
     * @return true if key is found in table
     */
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }

        int slotIndex = getSlotIndex(key);
        TableEntry<K, V> temp = this.table[slotIndex];

        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return true;
            } else {
                temp = temp.getNext();
            }
        }

        return false;
    }


    /**
     * Return true if value is stored in table,
     * otherwise return false.
     *
     * @param value value
     * @return true if value is found in table
     */
    public boolean containsValue(V value) {
        for (TableEntry<K, V> temp : this.table) {
            while (temp != null) {
                if (temp.getValue().equals(value)) {
                    return true;
                } else {
                    temp = temp.getNext();
                }
            }
        }

        return false;
    }


    /**
     * Remove (key, value) pair from table.
     *
     * @param key key
     */
    public void remove(K key) {
        if (key == null) {
            return;
        }

        int slotIndex = getSlotIndex(key);
        TableEntry<K, V> temp = this.table[slotIndex];

        /* key does not exist */
        if (temp == null) {
            return;
        }

        /* we are lucky, it is the first one */
        if (temp.getKey().equals(key)) {
            this.table[slotIndex] = temp.getNext();
            this.size--;
            return;
        }

        /* we are not lucky, have to loop through this slot */
        while (temp.getNext() != null && !temp.getNext().getKey().equals(key)) {
            temp = temp.getNext();
        }
        if (temp.getNext() != null && temp.getNext().getKey().equals(key)) {
            temp.setNext(temp.getNext().getNext());
            this.size--;
        }
    }


    /**
     * Return true if table is empty,
     * otherwise return false.
     *
     * @return true if table is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }


    /**
     * Return nice visual representation of the table.
     *
     * @return whole table in one String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.table.length; i++) {
            TableEntry<K, V> temp = this.table[i];

            if (temp != null) {
                sb.append(i)
                        .append(") ")
                        .append(temp.toString())
                        .append("\n");
            }
        }

        return sb.toString();
    }


    /**
     * Helper class, represents one slot in hash table.
     * <p>
     * TableEntry<K, V> next points to next slot in case of overflow,
     * otherwise it is equal to null.
     *
     * @param <K> key type
     * @param <V> value type
     */
    private static class TableEntry<K, V> {


        private final K key;
        private V value;
        private TableEntry<K, V> next = null;


        public TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }


        public K getKey() {
            return key;
        }


        public V getValue() {
            return value;
        }


        public void setValue(V value) {
            this.value = value;
        }


        public TableEntry<K, V> getNext() {
            return next;
        }


        public void setNext(TableEntry<K, V> next) {
            this.next = next;
        }


        /**
         * Return nice visual representation of slot and its linked slots.
         *
         * @return whole slot in one String
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("(")
                    .append(this.key.toString())
                    .append(" : ")
                    .append(this.value.toString())
                    .append(")");

            if (next != null) {
                sb.append(" -> ")
                        .append(next.toString());
            }

            return sb.toString();
        }


    }


}
