package main;


class HashMap {
    private final int TABLE_SIZE = 128;
    private HashEntry[] table;
    
    public HashMap() {
        table = new HashEntry[TABLE_SIZE];
    }
    
    private int hashFunction(int key) {
        return Math.abs(key) % TABLE_SIZE;
    }
    
    public int get(int key) {
        int hash = hashFunction(key);
        int startHash = hash;

        while (table[hash] != null) {
            if (table[hash].getKey() == key) {
                return table[hash].getValue();
            }
            hash = (hash + 1) % TABLE_SIZE;

            if (hash == startHash) break;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        int hash = hashFunction(key);
        int startHash = hash;

        while (table[hash] != null) {
            if (table[hash].getKey() == key) {
                table[hash].setValue(value);
                return;
            }
            hash = (hash + 1) % TABLE_SIZE;

            if (hash == startHash) {
                throw new RuntimeException("HashMap is full!");
            }
        }
        
        table[hash] = new HashEntry(key, value);
    }}