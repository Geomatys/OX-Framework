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
package org.n52.oxf.xmlbeans.parser;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlValidationError;
import org.n52.oxf.xml.XMLConstants;

/**
 * Allow sa:SamplingPoint when gml:AbstractFeature is expected.<br />
 * <b>SamplingPoint</b>@http://www.opengis.net/sampling/1.0 substitutes<br />
 * <b>SamplingFeature</b>@http://www.opengis.net/sampling/1.0 substitutes<br />
 * <b>_Feature</b>@http://www.opengis.net/gml<br />
 * <br />
 * @author <a href="mailto:e.h.juerrens@52north.org">Eike Hinderk J&uuml;rrens</a>
 *
 */
public class SASamplingPointCase extends AbstractLaxValidationCase {

    private static SASamplingPointCase instance = null;

    private SASamplingPointCase() {}

    public static SASamplingPointCase getInstance() {
        if (instance == null) {
            instance = new SASamplingPointCase();
        }
        return instance;
    }

    @Override
    public boolean shouldPass(final XmlValidationError xve) {
        final QName offending = xve.getOffendingQName();
        final List<?> expected = xve.getExpectedQNames();
        return offending != null && offending.equals(XMLConstants.QN_SA_1_0_SAMPLING_POINT) && // correct substitution
                expected != null && expected.contains(XMLConstants.QN_GML_ABSTRACT_FEATURE); // correct super class
    }
}
