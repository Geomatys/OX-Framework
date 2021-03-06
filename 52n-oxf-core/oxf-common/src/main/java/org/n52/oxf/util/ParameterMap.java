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
package org.n52.oxf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated
 *
 * TODO add new class and workflow to deprecated tag
 *
 * @author <a href="mailto:broering@52north.org">Arne Broering</a>
 */
@Deprecated
public class ParameterMap {

    Map<String, String> paramMap;

    public final static String PARAMETER_PATTERN = "([^=&]+=[^=&]*&?)+";


    /**
     * @param parameterString
     *        should look like this: "param1=value1&amp;param2=value2&amp;...&amp;paramN=valueN" <br>
     *        but also an empty 'value' is allowed (e.g.: "param1=&amp;param2=value2&amp;...")
     */
    public ParameterMap(String parameterString) {
        paramMap = new HashMap<>();

        if(parameterString.matches(PARAMETER_PATTERN)) {
            String[] paramParts = parameterString.split("&");

            for(String paramPart : paramParts){
                String[] paramAndValue = paramPart.split("=");
                if (paramAndValue.length > 1) {
                    paramMap.put(paramAndValue[0], paramAndValue[1]);
                }
                else {
                    paramMap.put(paramAndValue[0], null);
                }
            }
        }
        else{
            throw new IllegalArgumentException("paramString does not match the pattern, received: " + parameterString);
        }
    }

    public String getParameterValue(String caseInsensitiveName) {
        if (this.paramMap != null && caseInsensitiveName != null) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                if(key.equalsIgnoreCase(caseInsensitiveName)){
                    return value;
                }
            }
        }
        return null;
    }

}
