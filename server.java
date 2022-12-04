import java.io.*;
import java.net.*;

// Server class
class server {
	public static void main(String[] args)
	{
		ServerSocket server = null;

		try {

			// Server berjalan pada port 1234
			server = new ServerSocket(1234);
			server.setReuseAddress(true);
			int cNumber = 0;

			// Menjalankan infinite loop untuk mendapatkan
			// Request dari client
			System.out.println("Server Started....");
			while (true) {

				cNumber++;
				// Object Socket untuk menerima permintaan dari client
				Socket client = server.accept();

				// Menampilkan bahwa client baru terkoneksi ke server
				System.out.println("New client connected : "
								+ client.getInetAddress()
										.getHostAddress()
								+ " || "
								+ "Client Number : " + cNumber);

				// Membuat object thread yang baru
				ClientHandler clientSock
					= new ClientHandler(client, cNumber);

				// Thread ini akan mengendalikan client secara terpisah
				new Thread(clientSock).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;
		int clientNumber;

		// Constructor
		public ClientHandler(Socket socket, int cNumber)
		{
			this.clientSocket = socket;
			this.clientNumber = cNumber;
		}

		public void run()
		{
			PrintWriter out = null;
			BufferedReader in = null;
			try {
					
				// Mengambil nilai outputstream dari client
				out = new PrintWriter(
					clientSocket.getOutputStream(), true);

				// Mengambil InputStream dari client
				in = new BufferedReader(
					new InputStreamReader(
						clientSocket.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {
					// Menulis pesan yang diterima dari
					// client
					System.out.printf(
						" Sent from the client " + clientNumber + " : %s\n",
						line);
					out.println(line);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						clientSocket.close();
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
