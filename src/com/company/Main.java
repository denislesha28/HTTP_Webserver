package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        int portNumber = 1111;
        List<String> messages=new ArrayList<>();
        List<String> headers=new ArrayList<>();
        String clientRequest;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            if(clientSocket!=null) {
                System.out.println("Connected");
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            RequestContext handler=new RequestContext();
            handler.readHeader(in);
            clientRequest=handler.readHTTPVerb();
            if(clientRequest.compareTo("GET")==0){
                handler.printMessages(messages);
            }
            else if (clientRequest.compareTo("POST")==0){
                /*
                String regex = "messages\\/[0-9]";
                String request = handler.readRequest(in);
                Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(request);
                if(matcher.matches()){
                    System.out.println("True");
                }
                */
                handler.savePayload(messages,in);
                handler.saveHTTPHeader(headers,in);
            }
            else if (clientRequest.compareTo("PUT")==0){
                System.out.println(clientRequest.length()-1);
                handler.updatePayloadAt(clientRequest.length()-1,messages,in);
                handler.updateHTTPHeader(clientRequest.length()-1,headers);
            }
            else if (clientRequest.compareTo("DELETE")==0){
                handler.deletePayloadAt(clientRequest.length()-1,messages);
                handler.deleteHTTPHeader(clientRequest.length()-1,messages);
            }
            int temp=handler.saveHTTPHeader(headers,in);
            int responseId=handler.savePayload(messages,in);
            out.print("CKYADWDAWDWADcu");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
