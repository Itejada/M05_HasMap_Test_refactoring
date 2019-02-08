package ex2;

/* REFACCIÓ: */
public class Main {
    public static void main(String[] args) {
        HashTable2 hashTable2 = new HashTable2();

        // Put some key values.
        for (int i = 0; i < 30; i++) {
            final String key = String.valueOf(i);
            hashTable2.put(key, key);
        }

        // Print the HashTable structure
        log("****   HashTable  ***");
        log(hashTable2.toString());
        log("\nValue for key(20) : " + hashTable2.get("20"));
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}