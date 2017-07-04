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
package org.n52.oxf.sps.v100;

import org.n52.ows.request.getcapabilities.GetCapabilitiesParameters;
import org.n52.ows.request.getcapabilities.GetCapabilitiesRequest;
import org.n52.oxf.request.Request;
import org.n52.oxf.sps.SpsAdapter;

public class SpsAdapterV100 extends SpsAdapter {

    private final String serviceUrl;

    public SpsAdapterV100(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public boolean isSupporting(String version) {
        return "1.0.0".equals(version);
    }

    public GetCapabilitiesRequest createGetCapabilitiesRequest(GetCapabilitiesParameters parameters) {
        return new GetCapabilitiesRequest(parameters);
    }

    public void send(Request request) {
        request.sendViaGet(serviceUrl, httpClient);
    }

}
