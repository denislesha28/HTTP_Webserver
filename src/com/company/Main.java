package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
            clientRequest=handler.readHTTPVerb(in);
            if(clientRequest.compareTo("GET")==0){

            }
            else if (clientRequest.compareTo("POST")==0){

            }
            else if (clientRequest.compareTo("PUT")==0){

            }
            else if (clientRequest.compareTo("DELETE")==0){

            }
            int responseId=handler.saveHTTPHeader(headers,in);
            int responseIdMessage=handler.savePayload(messages,in);
            System.out.println(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
