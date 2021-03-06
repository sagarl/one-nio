package one.nio.pool;

import one.nio.net.ConnectionString;
import one.nio.net.Socket;

import java.io.IOException;

public class SocketPool extends Pool<Socket> {
    protected String host;
    protected int port;
    protected int readTimeout;
    protected int connectTimeout;

    public SocketPool(ConnectionString conn, int defaultPort) throws IOException {
        super(conn.getIntParam("clientMinPoolSize", 0),
              conn.getIntParam("clientMaxPoolSize", 10),
              conn.getIntParam("timeout", 3000));
        this.host = conn.getHost();
        this.port = conn.getPort() != 0 ? conn.getPort() : defaultPort;
        this.readTimeout = conn.getIntParam("readTimeout", timeout);
        this.connectTimeout = conn.getIntParam("connectTimeout", readTimeout);
        initialize();
    }

    @Override
    public Socket createObject() throws PoolException {
        Socket socket = null;
        try {
            socket = Socket.create();
            socket.setKeepAlive(true);
            socket.setNoDelay(true);
            socket.setTimeout(connectTimeout);
            socket.connect(host, port);
            socket.setTimeout(readTimeout);
            return socket;
        } catch (Exception e) {
            if (socket != null) socket.close();
            throw new PoolException(e);
        }
    }

    @Override
    public void destroyObject(Socket socket) {
        socket.close();
    }
}
