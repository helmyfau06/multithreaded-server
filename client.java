import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class client {
	
	// driver code
	public static void main(String[] args)
	{
		// Membuat koneksi dengan menyediakan host dan port
		// nomor
		try (Socket socket = new Socket("localhost", 1234)) {
			
			// Menulis ke server
			PrintWriter out = new PrintWriter(
				socket.getOutputStream(), true);

			// Membaca dari server
			BufferedReader in
				= new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// Membuat object scanner 
			Scanner sc = new Scanner(System.in);
			String line = null;

			while (!"exit".equalsIgnoreCase(line)) {
				
				// Membaca dari user
				line = sc.nextLine();

				// Mengirim user input ke server
				out.println(line);
				out.flush();

				// Menampilkan balasan dari server
				System.out.println("Server replied "
								+ in.readLine());
			}
			
			// Menutup object scanner
			sc.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
