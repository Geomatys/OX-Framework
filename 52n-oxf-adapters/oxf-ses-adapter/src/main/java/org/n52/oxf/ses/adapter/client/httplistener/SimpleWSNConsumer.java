/*
 * ﻿Copyright (C) 2012-2017 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the Free
 * Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of the
 * following licenses, the combination of the program with the linked library is
 * not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed under
 * the aforementioned licenses, is permitted by the copyright holders if the
 * distribution is compliant with both the GNU General Public License version 2
 * and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.n52.oxf.ses.adapter.client.httplistener;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple HTTP client (based on NanoHTTPD) to receive
 * WS-N notifications.
 *
 * @author matthes rieke
 *
 */
public class SimpleWSNConsumer extends NanoHTTPD implements IWSNConsumer {

    private static final String HTTP_NOCONTENT = "204 No Content";

    private HttpListener listener;
    private URL publicURL;

    public SimpleWSNConsumer(int port, String publicUrl) throws IOException, InterruptedException {
        this(port, publicUrl, new File("."), null);
    }


    public SimpleWSNConsumer(int port, String publicUrl, File wwwroot, HttpListener l) throws IOException, InterruptedException {
        super(port, wwwroot);
        this.listener = l;
        this.publicURL = new URL(publicUrl);
    }

    @Override
    protected Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        String data = parms.getProperty(POST_BODY);

        if (data == null) {
            data = parseURLEncoded(parms);
        }

        String resp = null;
        if (data != null && !data.isEmpty() && this.listener != null) {
            resp = this.listener.processRequest(data, uri, method, header);
        }

        if (resp == null || resp.isEmpty()) {
            return new NanoHTTPD.Response(HTTP_NOCONTENT, null, resp);
        } else {
            return new NanoHTTPD.Response(HTTP_OK, MIME_XML, resp);
        }
    }


    private String parseURLEncoded(Properties parms) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Object, Object> o : parms.entrySet()) {
            sb.append(o.getKey().toString())
                    .append("=")
                    .append(o.getValue().toString());
        }
        return sb.toString();
    }

    @Override
    public void setListener(HttpListener listener) {
        this.listener = listener;
    }

    @Override
    public URL getPublicURL() {
        return this.publicURL;
    }

    public static void main(String[] args) {
        final Logger LOG = LoggerFactory.getLogger(SimpleWSNConsumer.class);
        try {
            SimpleWSNConsumer sc = new SimpleWSNConsumer(8082, "http://localhost:8082");
            sc.setListener(new HttpListener() {

                @Override
                public String processRequest(String request, String uri, String method,
                        Properties header) {
                    synchronized (LOG) {
                        LOG.info("Received reqeust for {}:", uri);
                        LOG.info(request);
                    }

                    return null;
                }
            });
        } catch (IOException | InterruptedException e) {
            LOG.error("Exception thrown: ", e);
        }

        while (true);
    }

}
