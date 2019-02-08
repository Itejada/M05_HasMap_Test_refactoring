package ex3;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.

import ex3.HashEntry;

import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;

public class HashTable3 {
    private int INITIAL_SIZE = 16;
    private int size = 0;
    private HashEntry[] entries = new HashEntry[INITIAL_SIZE];

    public int size() {
        return this.size;
    }

    public int realSize() {
        return this.INITIAL_SIZE;
    }

    public void put(String key, String value) {
        final HashEntry hashEntry = new HashEntry(key, value);
        int has=getHash(key);
        HashEntry temp = entries[has];

            if (temp != null) {

//                System.out.println(entries.length);
//                for (int i = 1; i <(INITIAL_SIZE) ; i++) {
//                    String string=Integer.toString(i);
//                    int hasItem=getHash(string);
//                    System.out.println(string);
//
//                    entries2[hasItem]=entries[hasItem];
//
//                }
                INITIAL_SIZE*=2;
                HashEntry[] entries2 = new HashEntry[INITIAL_SIZE];

                for(HashEntry item : entries){
                if (item == null) {
                    continue;
                }
                int hasItem=getHash((String)item.key);

                if( entries2[hasItem]==entries[hasItem]) hasItem=hasItem*2;
                entries2[hasItem]=entries[hasItem];
            }
                entries=entries2;
                entries[getHash(key)] = hashEntry;

                size += 1;
            }else{
                entries[has] = hashEntry;
                size += 1;
            }


    }


    public Object get(String key) {
        return getHashEntry(key, entries[getHash(key)]).value;
    }

    private HashEntry getHashEntry(String key, HashEntry temp) {
        HashEntry tempNull = new HashEntry(null, null);
        while (!temp.key.equals(key) && temp.next != null) {
            temp = temp.next;

        }
        if (!temp.key.equals(key)) return tempNull;

        return temp;
    }

    public void drop(String key) {
        int hash = getHash(key);
        HashEntry temp = entries[hash];
        temp = getHashEntry(key, temp);
        if (temp.prev == null && temp.next == null) {
            entries[hash] = null;//esborrar element únic (no col·lissió)
        } else {
            while (temp.next != null) {
                temp.key = temp.next.key;
                temp.value = temp.next.value;
                temp = temp.next;
            }
            temp.prev.next = temp.next;//esborrem temp, per tant actualitzem el següent de l'anterior
            size -= 1;
        }

        // }
    }

    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return key.hashCode() % INITIAL_SIZE;
    }


    @Override
    public String toString() {
//        int bucket = entries.;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if (entry == null) {
                continue;
            }
            hashTableStr.append("\n bucket[")
                    .append(getHash((String) entry.key))
                    .append("] = ")
                    .append(entry.toString());
        //    bucket++;
            HashEntry temp = entry.next;
            while (temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    public ArrayList<String> getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1);
    }

    public ArrayList<String> getCollisionsForKey(String key, int quantity) {
        /*
          Main idea:
          alphabet = {0, 1, 2}

          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"

          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList();
        ArrayList<String> foundKeys = new ArrayList();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() - 1;

        while (foundKeys.size() < quantity) {
            //building current key
            String currentKey = "";
            for (int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if (!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current) + 1);

            //overflow over the alphabet on current!
            if (newKey.get(current) == alphabet.length) {
                int previous = current;
                do {
                    //increasing the previous to current alphabet key
                    previous--;
                    if (previous >= 0) newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for (int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if (previous < 0) newKey.add(0);

                current = newKey.size() - 1;
            }
        }

        return foundKeys;
    }


}