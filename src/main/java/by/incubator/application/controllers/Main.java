package by.incubator.application.controllers;

import by.incubator.application.players.impl.hardLevel.Node;

import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String crossPath = "src/main/resources/nodes/crossNode";
        String zeroPath = "src/main/resources/nodes/zeroNode";
        int[] emptyArr = new int[9];

        System.out.println(new Date());

        Node crossNode = new Node(emptyArr, true);
        System.out.println(crossNode);
        System.out.println(new Date());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(crossPath))) {
            oos.writeObject(crossNode);
            System.out.println("Cross node has been written " + new Date());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node newCrossNode;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(crossPath))) {
            newCrossNode = ((Node) ois.readObject());
            System.out.println("New cross node has been read" + new Date());
            System.out.println(newCrossNode);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

        System.out.println(new Date());

        Node zeroNode = new Node(emptyArr, false);
        System.out.println(zeroNode);
        System.out.println(new Date());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(zeroPath))) {
            oos.writeObject(zeroNode);
            System.out.println("Zero node has been written " + new Date());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node newZeroNode;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(zeroPath))) {
            newZeroNode = ((Node) ois.readObject());
            System.out.println("New zero node has been read" + new Date());
            System.out.println(newZeroNode);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }
}

class Person implements Serializable {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
