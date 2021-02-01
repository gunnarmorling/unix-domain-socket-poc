/**
 *  License: Apache License, Version 2.0
 *  See the LICENSE file in the root directory or <https://www.apache.org/licenses/LICENSE-2.0>.
 */
package dev.morling.demos.uds;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnixDomainSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

import javax.net.SocketFactory;

/**
 * Doesn't work, JDK 16 Unix domain sockets don't support {@code Socket}.
 */
public class PostgresUnixDomainSocketFactory extends SocketFactory {

    @Override
    public Socket createSocket() throws IOException {
        var sc = SocketChannel.open(UnixDomainSocketAddress.of("/var/run/postgresql/.s.PGSQL.5432"));
        return sc.socket();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
            throws IOException, UnknownHostException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
            throws IOException {
        throw new UnsupportedOperationException();
    }
}
