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
package org.n52.oxf.sos.observation;

import static org.n52.oxf.sos.adapter.ISOSRequestBuilder.*;
import static org.n52.oxf.xml.XMLConstants.QNAME_OM_1_0_TRUTH_OBSERVATION;

import org.n52.oxf.sos.adapter.ISOSRequestBuilder;
import org.n52.oxf.xml.XMLConstants;

/**
 * Assembles parameters for a Boolean observation.
 */
public class BooleanObservationParameters extends ObservationParameters {

    /**
     * Creates truth observations of type {@link XMLConstants#QNAME_OM_1_0_TRUTH_OBSERVATION}. Adds
     * {@link ISOSRequestBuilder#INSERT_OBSERVATION_TYPE_TRUTH} as its type description to the parameter list.
     */
    public BooleanObservationParameters() {
        super(QNAME_OM_1_0_TRUTH_OBSERVATION);
        addParameterValue(INSERT_OBSERVATION_TYPE, INSERT_OBSERVATION_TYPE_TRUTH);
    }

    /**
     * Adds or replaces the (current) observation value.
     *
     * @param observationValue
     *        the observation value to add.
     */
    public void addObservationValue(final boolean observationValue) {
        addParameterValue(INSERT_OBSERVATION_VALUE_PARAMETER, Boolean.toString(observationValue));
    }

    @Override
    public boolean isValid() {
        return !isEmptyValue(INSERT_OBSERVATION_VALUE_PARAMETER);
    }
}
