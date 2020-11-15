package com.company;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
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
            RequestContext handler=new RequestContext();
            ListSerialiser listHandler=new ListSerialiser();
            messages=listHandler.deserialiseList(messages,true);
            headers=listHandler.deserialiseList(headers,false);
            String splitter = "[^\\d]+";
            int responseID=0;
            while(true) {
                    Socket clientSocket = serverSocket.accept();
                    if (clientSocket != null) {
                        //System.out.println("Connected");
                    }
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    handler.readHeader(in);
                    clientRequest = handler.readHTTPVerb();
                    String operation = handler.readRequest();
                    if (clientRequest.compareTo("GET") == 0 && operation.compareTo("quit ")!=0) {
                        handler.printMessages(messages);
                        out.println(handler.generateResponse("Operation successful "));
                        out.flush();
                    } else if (clientRequest.compareTo("POST") == 0) {
                        responseID = handler.savePayload(messages, in);
                        handler.saveHTTPHeader(headers, in);
                        out.println(handler.generateResponse("Created Message: " + responseID));
                        out.flush();
                    } else if (clientRequest.compareTo("PUT") == 0) {
                        int index;
                        String[] ids = operation.split(splitter);
                        index = Integer.parseInt(ids[1]);
                        index -= 1;
                        //System.out.println(operation.length() - 1);
                        handler.updatePayloadAt(index, messages, in);
                        handler.updateHTTPHeader(index, headers);
                        out.println(handler.generateResponse("Message " + index + 1 + " updated\n"));
                        out.flush();
                    } else if (clientRequest.compareTo("DELETE") == 0) {
                        int index;
                        String[] ids = operation.split(splitter);
                        index = Integer.parseInt(ids[1]);
                        index -= 1;
                        //System.out.println(index);
                        handler.deletePayloadAt(index, messages);
                        handler.deleteHTTPHeader(index, headers);
                        out.println(handler.generateResponse("OK"));
                        out.flush();

                    } else if (clientRequest.compareTo("GET") == 0 && operation.compareTo("quit ")==0){
                        System.out.println("Closing Server");
                        serverSocket.close();
                        clientSocket.close();
                        break;
                    }
                    else {
                        out.println(handler.generateResponse("Error"));
                        clientSocket.close();
                        System.out.println("Wrong command");
                    }

                listHandler.serialiseList(messages, true);
                listHandler.serialiseList(headers, false);
                clientSocket.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
