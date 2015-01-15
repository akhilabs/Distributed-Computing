import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {


	public boolean validateInput(String input) {
		// System.out.println("Inside validateInput func");
		// System.out.println("input arg: " + input);
		if (input.equals("1")) {
			return true;
		} else if (input.equals("2")) {
			return true;
		} else if (input.equals("3")) {
			return true;
		}
		return false;
	}

	public String constructInputMessage(String selectedOption,
			BufferedReader userInput) throws IOException {
		String returnValue = null;
		if (selectedOption.equals("1")) {
			System.out.println("Enter the key");
			String key = userInput.readLine();
			System.out.println("Enter the value");
			String value = userInput.readLine();
			returnValue = selectedOption + ":" + key + ":" + value;
		} else if (selectedOption.equals("2") || selectedOption.equals("3")) {
			System.out.println("Enter the key");
			String key = userInput.readLine();
			returnValue = selectedOption + ":" + key;
		}
		return returnValue;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader in = null;
		Socket client = null;
		PrintWriter out = null;
		BufferedReader input = null;
		try {
			TCPClient tcpClient = new TCPClient();
			System.out.println("Enter the hostname");
			input = new BufferedReader(new InputStreamReader(System.in));
			String hostName = input.readLine();
			System.out.println("Enter the port number of the server");
			int port = Integer.parseInt(input.readLine());
			// System.out.println("hostname: " + hostName + " " + "port: " +
			// port);
			while (true) {
				try {
					client = new Socket(hostName, port);
					out = new PrintWriter(client.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(
							client.getInputStream()));
					System.out.println("Enter 1 for PUT");
					System.out.println("Enter 2 for GET");
					System.out.println("Enter 3 for DELETE");
					System.out.println("Enter 4 to quit");
					String readInput = input.readLine();
					readInput = readInput.trim();
					// System.out.println("Entered option: " + readInput);
					if (readInput == null) {
						continue;
					}
					if (readInput.equals("4")) {
						System.out.println("Exiting");
						System.exit(0);
					}
					boolean isInputValid = tcpClient.validateInput(readInput);
					// System.out.println("isInputValid: " + isInputValid);
					if (isInputValid) {
						String msg = tcpClient.constructInputMessage(readInput,
								input);
						// System.out.println("Constructed message: " + msg);
						out.println(msg);
						out.flush();
						if ((readInput = in.readLine()) != null) {
							System.out.println(readInput);
						}
					} else {
						System.out.println("Input not valid");
						continue;
					}
				} finally {
					client.close();
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// System.out.println("Inside finally");
			try {
				if (in != null) {
					in.close();
				}
				if (client != null) {
					client.close();
				}
				if (out != null) {
					out.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
