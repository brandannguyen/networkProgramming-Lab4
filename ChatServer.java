import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	//Set a port number to anything you want, just make sure it matches -Brandan
	private static final int portNum = 5678;

	private int serverPort;
	private List<ClientThread> clients;

	public static void main(String[] args) {
		ChatServer server = new ChatServer(portNum);
		server.startServer();
	}

	public ChatServer(int portNumber) {
		this.serverPort = portNumber;
	}

	public List<ClientThread> getClients() {
		return clients;
	}

	private void startServer() {
		clients = new ArrayList<ClientThread>();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(serverPort);
			acceptClients(serverSocket);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + serverPort);
			System.exit(1);
		}
	}

	private void acceptClients(ServerSocket serverSocket) {

		System.out.println("Server starts port: " + serverSocket.getLocalSocketAddress());
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Accepts User IP: " + socket.getRemoteSocketAddress());
				ClientThread client = new ClientThread(this, socket);
				Thread thread = new Thread(client);
				thread.start();
				clients.add(client);
			} catch (IOException ex) {
				System.out.println("Accept failed on: " + serverPort);
			}
		}
	}
}
