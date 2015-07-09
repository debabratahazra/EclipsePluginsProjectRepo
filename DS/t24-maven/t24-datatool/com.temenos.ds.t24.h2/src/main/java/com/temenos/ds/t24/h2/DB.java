package com.temenos.ds.t24.h2;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.Driver;
import org.h2.tools.Server;

/**
 * Database.
 *
 * @author Michael Vorburger
 */
public class DB {

	// Do NOT use any static here, please

	private String dbName = "TAFJDB";
	private String userName = "tafj";
	private String password = "tafj";

	private final File directory; // The directory where the TAFJDB.h2.db and the UD/ subdir. are in
	private int port = 0;         // 0 = auto select.  h2 default is 9092 (this is hard-coded elsewhere in TAFJ Home properties creation, but never should be, here)

	private Server server;
	private Connection keptAliveOpenConnection;

	public DB(File directory, int port) {
		super();
		this.directory = directory;
		if (!directory.exists())
			throw new IllegalArgumentException("Does not exist: " + directory.toString());
		if (!directory.isDirectory())
			throw new IllegalArgumentException("Is not a directory: " + directory.toString());
		this.port = port;
	}

	public DB(String directory, int port) {
		this(new File(directory), port);
	}

	public DB start() throws SQLException {
		checkRunning();

		File dbFile = new File(directory, getName() + ".h2.db");
		if (!dbFile.exists() && !dbFile.isFile())
			throw new IllegalArgumentException("Does not exist: " + dbFile.toString());

		if (port > 0 && !isPortAvailable(port)) {
			// TODO use slf4j with WARN log, instead of sysout
			System.out.println("There already is a H2 TCP server running on port " + port + ", so not starting another one");
			return this;
		}

		// server = new Server();
		// server.runTool("-tcp", "-tcpPort", Integer.toString(port), "-tcpAllowOthers", "-baseDir", directory.getPath());
		// Instead of above, we have to do this, copy/pasted from server.runTool(), so that we can find the chosen port via getService() (if it was 0)
        server = Server.createTcpServer("-tcp", "-tcpPort", Integer.toString(port), "-tcpAllowOthers", "-baseDir", directory.getPath());
        server.start();
        System.out.println(server.getStatus());
        // tcp.setShutdownHandler(this);

		this.port = server.getService().getPort(); // NPE: server.getPort(); // strange? No, needed if port == 0.

		// We now obtain, and keep, a connection to "warm up" the DB
		keptAliveOpenConnection = openNewConnection();

		return this;
	}

	private void checkRunning() {
		if (isRunning())
			throw new IllegalStateException("Already started");
	}

	public void stop() throws SQLException {
		if (!isRunning())
			throw new IllegalStateException("Never started (or already stopped)");

		keptAliveOpenConnection.close();
		server.stop();
		server.shutdown();
	}

	private Connection openNewConnection() throws SQLException {
		long t = System.currentTimeMillis();
		Driver.load();
		Connection jdbcConnection = DriverManager.getConnection(getJdbcUrl(), userName, password);
		System.out.println((System.currentTimeMillis() - t)/1000 + "s taken to start up h2 DB"); // TODO use slf4j logger instead of sysout
		return jdbcConnection;
	}

	public boolean isRunning() {
		return server != null;
	}

	public File getDirectory() {
		return directory;
	}

	private boolean isPortAvailable(int port) {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
				}
		}
	}

	public String getJdbcUrl() {
		return "jdbc:h2:tcp://localhost:" + getPort() + "/" + getName() + ";DB_CLOSE_ON_EXIT=FALSE;MODE=Oracle;TRACE_LEVEL_FILE=0;TRACE_LEVEL_SYSTEM_OUT=0;FILE_LOCK=NO;IFEXISTS=TRUE;CACHE_SIZE=8192";
	}

	public DB setName(String name) {
		checkRunning();
		this.dbName = name;
		return this;
	}

	public String getName() {
		return dbName ;
	}

	public String getUserName() {
		return userName;
	}

	public DB setUserName(String userName) {
		checkRunning();
		this.userName = userName;
		return this;
	}

	public DB setPassword(String password) {
		checkRunning();
		this.password = password;
		return this;
	}

	// Intentionally no getPassword()

	/**
	 * Get Port.
	 * This may be different than the one passed to the constructor;
	 * IFF 0 was passed to the constructor, then after start() was called
	 * this is the real random free port number that was actually chosen.
	 * @return TCP/IP port number.
	 */
	public int getPort() {
		return port;
	}
}
