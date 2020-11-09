package com.company;

import com.sun.beans.decoder.StringElementHandler;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HTTPRequestHandler {
    private StringBuilder payload;
    private String headerInfo;
    public void readHeader(BufferedReader in) throws IOException {
        while((headerInfo=in.readLine()).length() != 0){
            System.out.println(headerInfo);
        }
    }

    public void readPayload(BufferedReader in)throws IOException{
        payload = new StringBuilder();
        while(in.ready()){
            payload.append((char) in.read());
        }
        System.out.println("Payload data is: "+payload.toString());
    }

    public void savePayload() throws IOException {
        String filepath="messages";
        // find all occurrences forward
        for (int i = -1; (i = payload.indexOf("userId", i + 1)) != -1; i++) {
            System.out.println(i);
        }
        File file=new File(filepath);
        boolean folder=file.mkdir();
        filepath=filepath+"\\"+payload.charAt(11);
        file=new File(filepath);
        if(folder){
            System.out.println("Successful Folder Creation");
        }
        else{
            System.out.println("Unsuccessful Folder Creation");
            return;
        }
        System.out.println(filepath);
        file.createNewFile();
        FileWriter writer=new FileWriter(file);
        writer.write(String.valueOf(payload));
        writer.flush();
        writer.close();
    }
}
