//
//  ========================================================================
//  Copyright (c) 1995-2012 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.websocket.core.extensions.mux.add;

import java.io.IOException;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.core.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.core.examples.echo.AdapterEchoSocket;
import org.eclipse.jetty.websocket.core.extensions.mux.MuxChannel;
import org.eclipse.jetty.websocket.core.extensions.mux.MuxException;
import org.eclipse.jetty.websocket.core.io.WebSocketSession;
import org.eclipse.jetty.websocket.core.io.event.EventDriver;
import org.eclipse.jetty.websocket.core.io.event.EventDriverFactory;

/**
 * Dummy impl of MuxAddServer
 */
public class DummyMuxAddServer implements MuxAddServer
{
    @SuppressWarnings("unused")
    private static final Logger LOG = Log.getLogger(DummyMuxAddServer.class);
    private AdapterEchoSocket echo;
    private WebSocketPolicy policy;
    private EventDriverFactory eventDriverFactory;

    public DummyMuxAddServer()
    {
        this.policy = WebSocketPolicy.newServerPolicy();
        this.eventDriverFactory = new EventDriverFactory(policy);
        this.echo = new AdapterEchoSocket();
    }

    @Override
    public String handshake(MuxChannel channel, String requestHandshake) throws MuxException, IOException
    {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 101 Switching Protocols\r\n");
        response.append("Connection: upgrade\r\n");
        // not meaningful (per Draft 08) hresp.append("Upgrade: websocket\r\n");
        // not meaningful (per Draft 08) hresp.append("Sec-WebSocket-Accept: Kgo85/8KVE8YPONSeyhgL3GwqhI=\r\n");
        response.append("\r\n");

        EventDriver websocket = this.eventDriverFactory.wrap(echo);
        WebSocketSession session = new WebSocketSession(websocket,channel,channel.getPolicy(),"echo");
        channel.setSession(session);
        channel.setSubProtocol("echo");
        channel.onOpen();
        session.onConnect();

        return response.toString();
    }
}
