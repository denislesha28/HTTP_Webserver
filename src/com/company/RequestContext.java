package com.company;

import com.sun.beans.decoder.StringElementHandler;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestContext {
    private StringBuilder payload;
    private String headerInfo;
    public List<String> messages=new ArrayList<>();
    private String filepath="messages";

    public void readHeader(BufferedReader in) throws IOException {
        while((headerInfo=in.readLine()).length() != 0){
            System.out.println(headerInfo);
        }
    }

    public void saveHTTPHeader(){


    }

    public String readHTTPVerb(BufferedReader in) throws IOException {
        String verb=in.readLine();
        verb=verb.substring(0-4);
        return verb;
    }

    public void readPayload(BufferedReader in)throws IOException{
        payload = new StringBuilder();
        while(in.ready()){
            payload.append((char) in.read());
        }
        // System.out.println("Payload data is: "+payload.toString());
    }

    public void savePayload() throws IOException {
        // find all occurrences forward
       /* for (int i = -1; (i = payload.indexOf("userId", i + 1)) != -1; i++) {
            System.out.println(i);
        }*/
        File file=new File(filepath);
        if(file.mkdirs()){
            System.out.println("Successful Folder Creation");
        }
        else if(file.isDirectory()){
            System.out.println("Folder already exists");
        }
        else{
            System.out.println("Unsuccessful Folder Creation");
            return;
        }
        filepath=filepath+"\\";
        int i=11;
        while(payload.charAt(i)!=','){
            filepath=filepath+payload.charAt(i);
            i++;
        }
        file=new File(filepath);
        file.createNewFile();
        FileWriter writer=new FileWriter(file);
        writer.write(String.valueOf(payload));
        writer.flush();
        writer.close();
    }


   /* public void savePayload() {
        messages.set(2,"ckyaaddawd");
        messages.set(payload.charAt(11),payload.toString());
    }
*/
}
