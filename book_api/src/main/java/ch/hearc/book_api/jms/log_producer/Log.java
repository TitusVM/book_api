package ch.hearc.book_api.jms.log_producer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Log {
	private String timestamp;
	private String action;
	private String ressource;
	private String adress;

	public Log(String action, String ressource) {
		this.action = action;
		this.ressource = ressource;
		this.adress = getAdress();

		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		this.timestamp = df.format(new Date()); // get current time in "hh:mm:ss" format
	}

	@Override
	public String toString() {
		return "[" + timestamp + "]:" + adress + " performed " + action + " on " + ressource;
	}

	// https://www.baeldung.com/java-get-ip-address Baeldung Magic
	public String getAdress() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("google.com", 80));
			return socket.getLocalAddress().getHostAddress();
		} catch (IOException e) {
			return "LocalAdressUnknown";
		}
	}
}
