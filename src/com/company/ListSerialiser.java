package com.company;

import java.io.*;
import java.util.List;

public class ListSerialiser {
    public  void serialiseList(List<String> list, boolean msg) throws IOException {
        FileOutputStream fos;
        if(msg) {
            fos = new FileOutputStream("message_list");
        }
        else{
            fos = new FileOutputStream("header_list");
        }
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(list);
        oos.close();
        fos.close();
    }

    public  List deserialiseList(List<String>list,boolean msg) throws IOException, ClassNotFoundException {
        FileInputStream fis=null;
        if(msg) {
            File tempFile = new File("message_list");
            if (tempFile.exists()) {
                fis = new FileInputStream("message_list");
            }
        }
        else {
            File tempFile = new File("header_list");
            if (tempFile.exists()) {
                fis = new FileInputStream("header_list");
            }
        }
        if(fis==null){
            System.out.println("Could not find a list");
            return list;
        }
        ObjectInputStream ois = new ObjectInputStream(fis);
        if(ois==null){
            System.out.println("Could not find a list");
            return list;
        }
        list = (List<String>) ois.readObject();

        ois.close();
        fis.close();
        return list;
    }
}
