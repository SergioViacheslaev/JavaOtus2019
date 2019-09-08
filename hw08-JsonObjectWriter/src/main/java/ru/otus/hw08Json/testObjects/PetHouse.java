package ru.otus.hw08Json.testObjects;

import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class PetHouse {
    private int houseNumber;
    private String address;
    private Cat cat;
    private Dog dog;

    private long speed;
    private short size;
    private float height;
    private double weight;

    private int[] intArray;
    private Dog[] dogArray;
    private Cat[] catArray;

    private Map<Integer, String> integerStringMap;
    private List<Byte> byteList;

    private String nullString = null;


    public PetHouse() {
        houseNumber = 101;
        address = "Животная, дом 20";
        cat = new Cat("Kitty", 5);
        dog = new Dog("Goofy", 3);

        speed = 32432423423L;
        size = 25;
        height = 150.5f;
        weight = 10d;

        intArray = new int[]{1, 2, 3, 4, 5};
        dogArray = new Dog[]{new Dog("Snoopy", 11), new Dog("Woolfy", 15)};
        catArray = new Cat[]{new Cat("Lissy", 11), new Cat("Merry", 15)};

        integerStringMap = new HashMap<>();
        integerStringMap.put(100, "Sto");
        integerStringMap.put(200, "Dveste");
        integerStringMap.put(300, "Trista");

        byteList = new ArrayList<>();
        byteList.add(Byte.valueOf("1"));
        byteList.add(Byte.valueOf("2"));
        byteList.add(Byte.valueOf("3"));


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetHouse petHouse = (PetHouse) o;

        if (houseNumber != petHouse.houseNumber) return false;
        if (speed != petHouse.speed) return false;
        if (size != petHouse.size) return false;
        if (Float.compare(petHouse.height, height) != 0) return false;
        if (Double.compare(petHouse.weight, weight) != 0) return false;
        if (address != null ? !address.equals(petHouse.address) : petHouse.address != null) return false;
        if (cat != null ? !cat.equals(petHouse.cat) : petHouse.cat != null) return false;
        if (dog != null ? !dog.equals(petHouse.dog) : petHouse.dog != null) return false;
        if (!Arrays.equals(intArray, petHouse.intArray)) return false;
        if (!Arrays.equals(dogArray, petHouse.dogArray)) return false;
        if (!Arrays.equals(catArray, petHouse.catArray)) return false;
        if (integerStringMap != null ? !integerStringMap.equals(petHouse.integerStringMap) : petHouse.integerStringMap != null)
            return false;
        if (byteList != null ? !byteList.equals(petHouse.byteList) : petHouse.byteList != null) return false;
        return nullString != null ? nullString.equals(petHouse.nullString) : petHouse.nullString == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = houseNumber;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (cat != null ? cat.hashCode() : 0);
        result = 31 * result + (dog != null ? dog.hashCode() : 0);
        result = 31 * result + (int) (speed ^ (speed >>> 32));
        result = 31 * result + (int) size;
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(intArray);
        result = 31 * result + Arrays.hashCode(dogArray);
        result = 31 * result + Arrays.hashCode(catArray);
        result = 31 * result + (integerStringMap != null ? integerStringMap.hashCode() : 0);
        result = 31 * result + (byteList != null ? byteList.hashCode() : 0);
        result = 31 * result + (nullString != null ? nullString.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PetHouse{" +
                "houseNumber=" + houseNumber +
                ", address='" + address + '\'' +
                ", cat=" + cat +
                ", dog=" + dog +
                ", speed=" + speed +
                ", size=" + size +
                ", height=" + height +
                ", weight=" + weight +
                ", intArray=" + Arrays.toString(intArray) +
                ", dogArray=" + Arrays.toString(dogArray) +
                ", catArray=" + Arrays.toString(catArray) +
                ", integerStringMap=" + integerStringMap +
                ", byteList=" + byteList +
                ", nullString='" + nullString + '\'' +
                '}';
    }
}
