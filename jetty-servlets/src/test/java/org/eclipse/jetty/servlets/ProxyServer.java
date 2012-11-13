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

package org.eclipse.jetty.servlets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ProxyServer
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        HandlerCollection handlers = new HandlerCollection();
        server.setHandler(handlers);

        // Setup proxy servlet
        ServletContextHandler context = new ServletContextHandler(handlers, "/", ServletContextHandler.SESSIONS);
        ServletHolder proxyServlet = new ServletHolder(ProxyServlet.class);
//        proxyServlet.setInitParameter("whiteList", "google.com, www.eclipse.org, localhost");
//        proxyServlet.setInitParameter("blackList", "google.com/calendar/*, www.eclipse.org/committers/");
        context.addServlet(proxyServlet, "/*");


        // Setup proxy handler to handle CONNECT methods
//        ConnectHandler proxy = new ConnectHandler();
//        proxy.setWhite(new String[]{"mail.google.com"});
//        proxy.addWhitelistHost("www.google.com");
//        handlers.addHandler(proxy);

        server.start();
    }

}