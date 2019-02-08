package ex2;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.

import java.util.ArrayList;

public class HashTable2 {
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
        // int hash = getHash(key);
        /** REFACCIÓ: (~ º◡º)~
         * Inline */
        HashEntry temp = entries[getHash(key)];
        /** REFACCIÓ: ~(º◡º ~)
         *  He echo refaccio a la variable enreies hash para que solo tenga que buscarlo una vez y pueda ser utilizada en toodo el codigo */
        if (temp == null) {
            entries[getHash(key)] = hashEntry;
            /** ERROR: ¯\_(ツ)_/¯
             * Sumo para controlar el size porque se rellena una posicion nula  */
            size += 1;

        } else {
            /** ERROR: (つ◉益◉)つ
             * Compruebo si la key actual no coincide con la key entrante para pasar a la siguiente  */
            while (temp.next != null && !temp.key.equals(key)) {
                temp = temp.next;
            }

            /**ERROR:  ლ(ಥ_ಥ ლ)
             * Compruebo si el objeto hasentry actual tiene la misma clave que la pasada por parametros y sustituyo el valor
             * Tu no sabes lo que me ha costado darme cuenta de mi pequeño fallo, para llorar (ノಠ益ಠ)ノ彡┻━┻ */
            if (temp.key.equals(hashEntry.key)) {
                temp.value = hashEntry.value;

            } else {
                temp.next = hashEntry;
                hashEntry.prev = temp;

                /** ERROR: ¯\_(ツ)_/¯
                 * Sigo controloando el size de forma manual, ya que se estan creando nuevos objetos*/
                size += 1;
            }
        }
    }


    /**
     * Returns 'null' if the element is not found.
     */
    public String get(String key) {
        //int hash = getHash(key);
        return getHashEntry(key, entries[getHash(key)]).value;
        // if (temp != null) {
        /**REFACCIÓ: Extraccio de metode, e cambiado todos los while que encontraban el hasentry a un metodo */
        //   temp= getHashEntry(key, temp);
        //   return getHashEntry(key, temp).value;
//        }
//        return null;
    }

    private HashEntry getHashEntry(String key, HashEntry temp) {
        HashEntry tempNull=new HashEntry(null,null);
            while (!temp.key.equals(key) && temp.next !=null) {
                temp = temp.next;

            }
            if (!temp.key.equals(key)) return tempNull;

        return temp;
    }

    public void drop(String key) {
          int hash = getHash(key);

        HashEntry temp = entries[hash];
        // if (temp != null) {
        /**REFACCIÓ: Extraccio de metode, e cambiado todos los while que encontraban el hasentry a un metodo */
        temp = getHashEntry(key, temp);
        /** ERROR: ( -◡•)>⌐■-■
         * Compruebo si el anterior y el posterior a temp actual son nulos, en caso afirmativo procedo ha borrar sin complicación*/
        if (temp.prev == null && temp.next == null) {
            entries[hash]=null;//esborrar element únic (no col·lissió)

            /** ERROR: ¯\_(ツ)_/¯
             * Sigo controloando el size de forma manual, ahora esta eliminando un objeto hasentry, como este no tiene colisiones, no nos preocupa ya que sabes que unicamente es uno, lo que nos lleva a la conclusion de que con restarle 1 al size basta */
            size -= 1;
        } else {
            /** ERROR: (∩╹□╹∩)*
             * Compruebo que el posterior al actual no este vacio, de esta manera, voy copiando los valores siguientes en el acutal, el ultimo se convierte en null(se explica abajo el porque) */
            while (temp.next != null) {
                temp.key = temp.next.key;
                temp.value = temp.next.value;
                temp = temp.next;
            }
            /** No se si es ERROR:
             * pero dado a mi while este if siempre sera falso, por lo que he tomado la decision de comentarlo(es broma) Enrrealidad el motivo es que jamas quedara un "hueco" en medo de las colisiones, por lo que no tendremos que redirigir los .next y .prev para saltarnos el hueco :D*/
//                if (temp.next != null)
//                    temp.next.prev = temp.prev;   //esborrem temp, per tant actualitzem l'anterior al següent
            temp.prev.next = temp.next;//esborrem temp, per tant actualitzem el següent de l'anterior
            /** ERROR: ¯\_(ツ)_/¯
             *  Aqui tambien restaremos 1, ya que justo la linea que tenemos encima convierte en nulo el ultimo objeto de la colision, esto tiene un porque, ya que hemos desplazado todos los datos una posicion  hacia atras, esto no borra el ultimo objeto, pero con la linea mencionada solucionamos el problema y reducimos el tamaño en 1, por lo tanto basta con restar 1   */
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
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if (entry == null) {
                continue;
            }
            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
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