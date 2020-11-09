package com.company;

import com.sun.beans.decoder.StringElementHandler;

import java.io.BufferedReader;
import java.io.IOException;

public class HTTPRequestHandler {
    public void readHeader(BufferedReader in) throws IOException {
        String headerInfo;
        while((headerInfo=in.readLine()).length() != 0){
            System.out.println(headerInfo);
        }
    }

    public void readPayload(BufferedReader in)throws IOException{
        StringBuilder payload = new StringBuilder();
        while(in.ready()){
            payload.append((char) in.read());
        }
        System.out.println("Payload data is: "+payload.toString());
    }

}
