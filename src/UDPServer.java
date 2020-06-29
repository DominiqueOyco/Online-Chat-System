
/**
 * CECS 327 ASSIGNMENT 3
 * Dominique Oyco - 014605758
 * Eric Curlett - 015921189
 * 
 * To compile this java file on the terminal (MAC/LINUX) or the command line (WINDOWS),
 * user must go to the directory where this java file is located and then type into the terminal/cmd:
 * 
 * 			javac UDPServer.java
 * 
 * After compiling this file, THE USER MUST RUN THIS FILE FIRST BEFORE THE CLIENT!
 * To run this file on the terminal or the cmd line, the user must have compiled the file first and afterwards
 * type the following:
 * 
 * 			java UDPServer port#
 * 
 * where 
 * - port# is the server port that the server will use to communicate with the client and 
 *   IT MUST BE THE SAME PORT AS THE CLIENT!
 */

import java.net.*;
import java.io.*;

public class UDPServer{
	public static void main(String args[]){
		System.out.println("Starting the server");
		DatagramSocket aSocket = null;
		try {
			int serverPort = Integer.parseInt(args[0]);
			aSocket = new DatagramSocket(serverPort);
			System.out.println("Server running on: " + serverPort);
			System.out.println("Waiting on connection...\n");
			
			byte [] buffer = new byte[1000];
      //Waits for input from clients and processes until closed
			while(true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				
				InetAddress address = request.getAddress();
				int portNum = request.getPort();
				System.out.println("Request from: " + address + " Port #: " + portNum);
				DatagramPacket reply = new DatagramPacket(request.getData(),
						request.getLength(), address, portNum);
				System.out.println("Echoing data back to client");
				aSocket.send(reply);
			}			
		}catch(SocketException e){
			System.out.println("Socket: " + e.getMessage());
		}catch(IOException e){
			System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
	}
}
