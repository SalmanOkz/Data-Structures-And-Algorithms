package main;

public class Main {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, 30);

        System.out.println("Value for key 1: " + map.get(1));
        System.out.println("Value for key 2: " + map.get(2));
        System.out.println("Value for key 3: " + map.get(3));

        map.put(1, 99);
        System.out.println("Updated value for key 1: " + map.get(1));
    }
}
