package com.company;

import com.sun.beans.decoder.StringElementHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.SPACE_SEPARATOR;

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
        return list.size();
    }

    public String readHTTPVerb(BufferedReader in) throws IOException {
        String verb="";
        for (int i=0;i<headerInfo.length();i++){
            if(headerInfo.charAt(i)==' '){
                break;
            }
            verb+=headerInfo.charAt(i);
        }
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
