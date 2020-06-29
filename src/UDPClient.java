/**
 * CECS 327 ASSIGNMENT 3
 * Dominique Oyco - 014605758
 * Eric Curlett - 015921189
 * 
 * To compile this java file on the terminal (MAC/LINUX) or the command line (WINDOWS),
 * user must go to the directory where this java file is located and then type into the terminal/cmd:
 * 
 * 			javac UDPClient.java
 * 
 * After compiling this file, THE USER MUST RUN UDPServer.java FIRST BEFORE THIS FILE!
 * To run this file on the terminal or the cmd line, the user must have compiled the file first and afterwards
 * type the following:
 * 
 * 			java UDPClient hostname port#
 * 
 * where 
 * - hostname is the server hostname
 * - port# is the server port that the client will use to communicate with the server and 
 * 	 IT MUST BE THE SAME PORT AS THE SERVER!
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {
	public static void main(String args[]) {
		boolean done = false;
		Scanner sc = new Scanner(System.in);
    //While not done, continue asking user for input to send to server
		while (!done){
			DatagramSocket aSocket = null;
			try {
				System.out.println("Enter message here(done to quit):");
				String input = sc.nextLine();
				if (input.equals("done")) {
					done = true;
					break;
				}
				aSocket = new DatagramSocket();
				byte [] m = input.getBytes(); //create byte array of message to send in datagram packet
				InetAddress aHost = InetAddress.getByName(args[0]);
				int serverPort = Integer.parseInt(args[1]);
				DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
				aSocket.send(request);
				byte[] buffer = new byte[1000];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(reply);
				System.out.println("Reply: " + new String(reply.getData()) + "\n");
				
			}catch(SocketException e){
				System.out.println("Socket: " + e.getMessage());
        //If socket error occurs break out of loop to initiate cleanup
        done = true;
			}catch(IOException e){
				System.out.println("IO: " + e.getMessage());
			}catch(NumberFormatException e){
				System.out.println("Error, for Port recieved: " + e.getMessage());
			}finally {if(aSocket != null) aSocket.close();}
		}
		if(sc != null) sc.close();
	}
}