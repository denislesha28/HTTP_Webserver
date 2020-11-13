package com.company;

import com.sun.beans.decoder.StringElementHandler;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestContext {
    private StringBuilder payload;
    private String headerInfo;

    public void readHeader(BufferedReader in) throws IOException {
        String line;
        while((line=in.readLine()).length() != 0){
            System.out.println(line);
            if(line.compareTo("null")==0){
                continue;
            }
            headerInfo=headerInfo+line+"\n";
        }
        headerInfo=headerInfo.replace("null","");
    }

    public int saveHTTPHeader(List<String> list,BufferedReader in) throws IOException {
        readHeader(in);
        list.add(headerInfo);
        System.out.println(headerInfo);
        return list.size();
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

    public int savePayload(List<String> list,BufferedReader in) throws IOException {
        readPayload(in);
        list.add(payload.toString());
        payload=null; // empty payload
        return list.size();
    }

}
