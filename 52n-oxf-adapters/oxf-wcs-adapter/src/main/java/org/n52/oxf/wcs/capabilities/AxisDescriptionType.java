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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2005.06.25 at 02:38:11 CEST
//


package org.n52.oxf.wcs.capabilities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "AxisDescriptionType", namespace = "http://www.opengis.net/wcs")
public class AxisDescriptionType
    extends AbstractDescriptionType
{

    @XmlElement(name = "values", namespace = "http://www.opengis.net/wcs", type = org.n52.oxf.wcs.capabilities.AxisDescriptionType.Values.class)
    protected org.n52.oxf.wcs.capabilities.AxisDescriptionType.Values values;
    @XmlAttribute(name = "refSys", namespace = "")
    protected String refSys;
    @XmlAttribute(name = "refSysLabel", namespace = "")
    protected String refSysLabel;
    @XmlAttribute(name = "semantic", namespace = "http://www.opengis.net/wcs")
    protected String semantic;

    /**
     * Gets the value of the values property.
     *
     * @return
     *     possible object is
     *     {@link org.n52.oxf.wcs.capabilities.AxisDescriptionType.Values}
     */
    public org.n52.oxf.wcs.capabilities.AxisDescriptionType.Values getValues() {
        return values;
    }

    /**
     * Sets the value of the values property.
     *
     * @param value
     *     allowed object is
     *     {@link org.n52.oxf.wcs.capabilities.AxisDescriptionType.Values}
     */
    public void setValues(org.n52.oxf.wcs.capabilities.AxisDescriptionType.Values value) {
        this.values = value;
    }

    /**
     * Gets the value of the refSys property.
     *
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    public String getRefSys() {
        return refSys;
    }

    /**
     * Sets the value of the refSys property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setRefSys(String value) {
        this.refSys = value;
    }

    /**
     * Gets the value of the refSysLabel property.
     *
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    public String getRefSysLabel() {
        return refSysLabel;
    }

    /**
     * Sets the value of the refSysLabel property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setRefSysLabel(String value) {
        this.refSysLabel = value;
    }

    /**
     * Gets the value of the semantic property.
     *
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    public String getSemantic() {
        if (semantic == null) {
            return "closed";
        } else {
            return semantic;
        }
    }

    /**
     * Sets the value of the semantic property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setSemantic(String value) {
        this.semantic = value;
    }

    @XmlAccessorType(value = XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Values
        extends ValueEnumType
    {

        @XmlElement(name = "default", namespace = "http://www.opengis.net/wcs", type = TypedLiteralType.class)
        protected TypedLiteralType _default;

        /**
         * Gets the value of the default property.
         *
         * @return
         *     possible object is
         *     {@link org.n52.oxf.wcs.capabilities.TypedLiteralType}
         */
        public TypedLiteralType getDefault() {
            return _default;
        }

        /**
         * Sets the value of the default property.
         *
         * @param value
         *     allowed object is
         *     {@link org.n52.oxf.wcs.capabilities.TypedLiteralType}
         */
        public void setDefault(TypedLiteralType value) {
            this._default = value;
        }

    }

}
