/**
 *  License: Apache License, Version 2.0
 *  See the LICENSE file in the root directory or <https://www.apache.org/licenses/LICENSE-2.0>.
 */
package dev.morling.demos.uds;

import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.SocketChannel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.vertx.core.net.impl.transport.Transport;

public class UnixDomainTransport extends Transport {

    @Override
    public ChannelFactory<? extends Channel> channelFactory(boolean domainSocket) {
        if (!domainSocket) {
            return super.channelFactory(domainSocket);
        }
        return () -> {
                try {
                    var sc = SocketChannel.open(StandardProtocolFamily.UNIX);
                    return new UnixDomainSocketChannel(null, sc);
                }
                catch(Exception e) {
                    throw new RuntimeException(e);
                }
            };
    }

    @Override
    public SocketAddress convert(io.vertx.core.net.SocketAddress address) {
        if (!address.isDomainSocket()) {
            return super.convert(address);
        }
        return UnixDomainSocketAddress.of(address.path());
    }
}
