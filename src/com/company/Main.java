package com.company;

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
            String splitter = "[^\\d]+";
            int responseID=0;
            while(true) {
                Socket clientSocket = serverSocket.accept();
                if(clientSocket!=null) {
                    System.out.println("Connected");
                }
                PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                handler.readHeader(in);
                clientRequest = handler.readHTTPVerb();
                String operation = handler.readRequest();
                //int index=operation.charAt(operation.length()-2) -'0';
                if (clientRequest.compareTo("GET") == 0) {
                    handler.printMessages(messages);
                } else if (clientRequest.compareTo("POST") == 0) {
                /*
                String regex = "messages\\/[0-9]";
                String request = handler.readRequest(in);
                Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(request);
                if(matcher.matches()){
                    System.out.println("True");
                }
                */
                    responseID=handler.savePayload(messages, in);
                    handler.saveHTTPHeader(headers, in);
                    String responseString= "HTTP/1.1 200 OK.\r\n"+
                                            "Server: Denis\r\n"+
                                            "Content-Type: text/html\r\n"+
                                            "Accept-Ranges: bytes\r\n"+
                                            "Content-Length:1\r\n\r\n"+
                                            "Created Message: "+responseID+"\r\n";
                    // Send parameters
                    out.println(responseString);
                    out.flush();
                } else if (clientRequest.compareTo("PUT") == 0) {
                    int index;
                    String[] ids = operation.split(splitter);
                    index = Integer.parseInt(ids[1]);
                    index -= 1;
                    System.out.println(operation.length() - 1);
                    handler.updatePayloadAt(index, messages, in);
                    handler.updateHTTPHeader(index, headers);
                } else if (clientRequest.compareTo("DELETE") == 0) {
                    int index;
                    String[] ids = operation.split(splitter);
                    index = Integer.parseInt(ids[1]);
                    index -= 1;
                    System.out.println(index);
                    handler.deletePayloadAt(index, messages);
                    handler.deleteHTTPHeader(index, headers);
                }
                else if(clientRequest.compareTo("QUIT")==0){
                    break;
                }
            }
            System.out.println(messages.size());
            System.out.println(headers.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
