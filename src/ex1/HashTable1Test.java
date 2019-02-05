package ex1;

import org.junit.jupiter.api.Assertions;
import original.HashTable;

import static org.junit.jupiter.api.Assertions.*;

class HashTable1Test {

    @org.junit.jupiter.api.Test
    void size() {
        HashTable1 ht = new HashTable1();
        Assertions.assertEquals(0, ht.size());
        ht.put("3", "Naipe");
        ht.put( "4","As");
        ht.put("3", "Casi");
        Assertions.assertEquals(2, ht.size());
        ht.put("5", "Por el ***");
        ht.drop("5");
        System.out.println("aqui nadie a visto nada ");
        Assertions.assertEquals(2, ht.size());



    }

    @org.junit.jupiter.api.Test
    void realSize() {
        HashTable ht = new HashTable();
        Assertions.assertEquals(16, ht.realSize());
    }

    @org.junit.jupiter.api.Test
    void put() {
        HashTable1 ht = new HashTable1();

       // ht.put("1", "Pera");
        /** Me resultaba más comodo definir un metodo que escribiera por mi el texto esperado, evitando asi errores hardcodeados
         * El funcionamiento es sencillo, la pasas el bucket, la key del map y el value esperado*/
        //Assertions.assertEquals(textoEsperado(0, 1, "Pera"), ht.toString());

        String key = ht.getCollisionsForKey("3", 100).get(1);
        ht.put("14", "Uno");

        ht.put("3", "Naipe");
        Assertions.assertEquals(textoEsperado(0, 14, "Uno")+" -> [3, Naipe]", ht.toString());
        ht.put("14","Dos");

        Assertions.assertEquals(textoEsperado(0, 14, "Dos")+" -> [3, Naipe]", ht.toString());

        ht.put("3", "Dovah");
        Assertions.assertEquals(textoEsperado(0, 14, "Dos")+" -> [3, Dovah]", ht.toString());





//        ht.put("0", "xd");
//        System.out.println(ht.toString());
//        Assertions.assertEquals(textoEsperado(0, 0, "xd")+""+textoEsperado(1, 1, "Manzana"), ht.toString());


        System.out.println(ht.toString());
    }

    @org.junit.jupiter.api.Test
    void get() {
        HashTable1 ht = new HashTable1();
        //colision forzada
        String key = ht.getCollisionsForKey("3", 1000).get(1);

        //puts
        ht.put(key, "jejeje");
        ht.put("3", "noo");
        ht.put("1", "Manzana");

        // Comparaciones con souts para vista rapida
        Assertions.assertEquals("noo", ht.get("3"));
        System.out.println("noo = " + ht.get("3"));

        Assertions.assertEquals("jejeje", ht.get(key));
        System.out.println("jejeje = " + ht.get(key));

        Assertions.assertEquals("Manzana", ht.get("1"));
        System.out.println("Manzana = " + ht.get("1"));

        //muestro la tabla entera
        System.out.println(ht.toString());
    }

    @org.junit.jupiter.api.Test
    void drop() {
        HashTable1 ht = new HashTable1();

        String key = ht.getCollisionsForKey("14", 100).get(1);
        ht.put("14", "Uno");

        ht.put("3", "Naipe");
        Assertions.assertEquals(textoEsperado(0, 14, "Uno")+" -> [3, Naipe]", ht.toString());

        ht.put("14","Dos");
        Assertions.assertEquals(textoEsperado(0, 14, "Dos")+" -> [3, Naipe]", ht.toString());

        ht.put("3", "Dovah");
        Assertions.assertEquals(textoEsperado(0, 14, "Dos")+" -> [3, Dovah]", ht.toString());

//        ht.drop("14");
//        Assertions.assertEquals(textoEsperado(0, 3, "Dovah"), ht.toString());

        ht.drop("3");
        Assertions.assertEquals(textoEsperado(0, 14, "Dos"), ht.toString());

        ht.put("3", "Dovah");
        Assertions.assertEquals(textoEsperado(0, 14, "Dos")+" -> [3, Dovah]", ht.toString());
        ht.put(key,"siguiente");
        Assertions.assertEquals(textoEsperado(0, 14, "Dos")+" -> [3, Dovah] -> [03, siguiente]", ht.toString());
        ht.drop("3");
        Assertions.assertEquals(textoEsperado(0, 14, "Dos")+" -> [03, siguiente]", ht.toString());








    }

    public String textoEsperado(int bucket, int key, String value) {
        String textoEsperado = "\n bucket[" + bucket + "] = [" + key + ", " + value + "]";
        return textoEsperado;
    }


}