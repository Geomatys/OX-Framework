//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.25 at 02:38:11 CEST 
//


package org.n52.oxf.wcsModel.version100.wcsCapabilities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "DCPTypeType", namespace = "http://www.opengis.net/wcs")
public class DCPTypeType {

    @XmlElement(name = "HTTP", namespace = "http://www.opengis.net/wcs", type = org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.class)
    protected org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP http;

    /**
     * Gets the value of the http property.
     * 
     * @return
     *     possible object is
     *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP}
     */
    public org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP getHTTP() {
        return http;
    }

    /**
     * Sets the value of the http property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP}
     */
    public void setHTTP(org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP value) {
        this.http = value;
    }

    @XmlAccessorType(value = XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class HTTP {

        @XmlElements(value = {
            @XmlElement(name = "Get", namespace = "http://www.opengis.net/wcs", type = org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Get.class),
            @XmlElement(name = "Post", namespace = "http://www.opengis.net/wcs", type = org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Post.class)
        })
        protected List<Object> getOrPost;

        protected List<Object> _getGetOrPost() {
            if (getOrPost == null) {
                getOrPost = new ArrayList<Object>();
            }
            return getOrPost;
        }

        /**
         * Gets the value of the getOrPost property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the getOrPost property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGetOrPost().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Get}
         * {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.DCPTypeType.HTTP.Post}
         * 
         */
        public List<Object> getGetOrPost() {
            return this._getGetOrPost();
        }

        @XmlAccessorType(value = XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Get {

            @XmlElement(name = "OnlineResource", namespace = "http://www.opengis.net/wcs", type = OnlineResourceType.class)
            protected OnlineResourceType onlineResource;

            /**
             * Gets the value of the onlineResource property.
             * 
             * @return
             *     possible object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public OnlineResourceType getOnlineResource() {
                return onlineResource;
            }

            /**
             * Sets the value of the onlineResource property.
             * 
             * @param value
             *     allowed object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public void setOnlineResource(OnlineResourceType value) {
                this.onlineResource = value;
            }

        }

        @XmlAccessorType(value = XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Post {

            @XmlElement(name = "OnlineResource", namespace = "http://www.opengis.net/wcs", type = OnlineResourceType.class)
            protected OnlineResourceType onlineResource;

            /**
             * Gets the value of the onlineResource property.
             * 
             * @return
             *     possible object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public OnlineResourceType getOnlineResource() {
                return onlineResource;
            }

            /**
             * Sets the value of the onlineResource property.
             * 
             * @param value
             *     allowed object is
             *     {@link org.n52.oxf.wcsModel.version100.wcsCapabilities.OnlineResourceType}
             */
            public void setOnlineResource(OnlineResourceType value) {
                this.onlineResource = value;
            }

        }

    }

}