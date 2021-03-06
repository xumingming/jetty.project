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

package org.eclipse.jetty.websocket.core.examples;

import org.eclipse.jetty.websocket.core.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.core.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.core.annotations.OnWebSocketFrame;
import org.eclipse.jetty.websocket.core.annotations.WebSocket;
import org.eclipse.jetty.websocket.core.api.WebSocketConnection;
import org.eclipse.jetty.websocket.core.io.event.EventCapture;
import org.eclipse.jetty.websocket.core.protocol.Frame;

@WebSocket
public class AnnotatedFramesSocket
{
    public EventCapture capture = new EventCapture();

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        capture.add("onClose(%d, %s)",statusCode,capture.q(reason));
    }

    @OnWebSocketConnect
    public void onConnect(WebSocketConnection conn)
    {
        capture.add("onConnect(%s)",conn);
    }

    @OnWebSocketFrame
    public void onFrame(Frame frame)
    {
        capture.add("onFrame(%s)",frame);
    }
}
