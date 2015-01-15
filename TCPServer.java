package org.adc.phase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements Runnable {

	Socket connServer = null;
	BufferedReader inputServer = null;
	PrintStream outputServer = null;
	static MapImplementation mapImpl = new MapImplementation();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String input;
		try {
			this.inputServer = new BufferedReader(new InputStreamReader(
					connServer.getInputStream()));
			this.outputServer = new PrintStream(
					this.connServer.getOutputStream(), true);
			input = this.inputServer.readLine();
			//System.out.println("Msg from client: " + input);
			if (input != null) {
				String[] arg = input.split(":");
				if (arg.length == 3) {
					mapImpl.put(arg[1], arg[2]);
					//System.out.println("Successfully stored");
					this.outputServer.println("Successfully stored");
				} else if (arg.length == 2) {
					if (arg[0].equals("2")) {
						System.out.println("arg[0] " + arg[0]);
						System.out.println("arg[1] " + arg[1]);
						this.outputServer
								.println("Key: " + mapImpl.get(arg[1]));
						//System.out.println("Key: " + mapImpl.get(arg[1]));
					} else if (arg[0].equals("3")) {
						System.out.println("arg[0] " + arg[0]);
						System.out.println("arg[1] " + arg[1]);
						mapImpl.delete(arg[1]);
						this.outputServer.println("Successfully deleted");
						//System.out.println("Successfully deleted");
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (this.connServer != null) {
					connServer.close();
				}
				if (this.inputServer != null) {
					inputServer.close();
				}
				if (this.outputServer != null) {
					outputServer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		TCPServer tcpServer = null;
		ServerSocket server = null;
		BufferedReader portReader = null;
		try {
			portReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the port number");
			String input = portReader.readLine();
			server = new ServerSocket(Integer.parseInt(input));
			while (true) {
				// create separate class for each functionality..
				// then no need to use dcpMaploader mp in this constructor..
				System.out.println("Waiting for client's connection");
				tcpServer = new TCPServer();
				tcpServer.connServer = server.accept();
				System.out.println("Connected.......");
				ExecutorService threadExe = Executors
						.newSingleThreadScheduledExecutor();
				threadExe.submit(tcpServer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (server != null) {
					server.close();
				}
				if (portReader != null) {
					portReader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
