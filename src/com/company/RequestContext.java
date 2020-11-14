package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestContext {
    private StringBuilder payload;
    private String headerInfo;

    public void readHeader(BufferedReader in) throws IOException {
        headerInfo="";
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
        list.add(headerInfo);
        return list.size();
    }

    public void updateHTTPHeader(int position,List<String> list){
        if(list.isEmpty()){
            System.out.println("Messages are Empty!!");
            return;
        }
        list.set(position,headerInfo);
    }

    public void deleteHTTPHeader(int position,List<String> list){
        if(list.isEmpty()){
            System.out.println("Messages are Empty!!");
            return;
        }
        list.remove(position);
    }

    public String readHTTPVerb() throws IOException {
        String verb="";
        for (int i=0;i<headerInfo.length();i++){
            if(headerInfo.charAt(i)==' '){
                break;
            }
            verb+=headerInfo.charAt(i);
        }
        return verb;
    }

    public String readRequest()throws IOException{
        String request="";
        for (int i=0;i<headerInfo.length();i++){
            if(headerInfo.charAt(i)=='/'){
                for (int j=i+1;j<headerInfo.length();j++){
                    request+=headerInfo.charAt(j);
                    if(headerInfo.charAt(j)==' '){
                        return request;
                    }
                }
            }
        }
        return request;
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
        System.out.println("Payload: "+payload.toString());
        payload=null; // empty payload
        return list.size();
    }


    public String returnPayloadAt(int position,List<String> list) throws IOException {
        return list.get(position);
    }

    public void updatePayloadAt(int position,List<String> list,BufferedReader in) throws IOException{
        if(list.isEmpty()){
            System.out.println("Messages are Empty!!");
            return;
        }
        readPayload(in);
        list.set(position,payload.toString());
        payload=null;
    }



    public void deletePayloadAt(int position,List<String> list) throws IOException{
        if(list.isEmpty()){
            System.out.println("Messages are Empty!!");
            return;
        }
        list.remove(position);
    }

    public void printMessages(List<String> list){
        System.out.println(list);
    }




}
