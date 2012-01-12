/**********************************************************************************
 Copyright (C) 2009
 by 52 North Initiative for Geospatial Open Source Software GmbH

 Contact: Andreas Wytzisk 
 52 North Initiative for Geospatial Open Source Software GmbH
 Martin-Luther-King-Weg 24
 48155 Muenster, Germany
 info@52north.org

 This program is free software; you can redistribute and/or modify it under the
 terms of the GNU General Public License version 2 as published by the Free
 Software Foundation.

 This program is distributed WITHOUT ANY WARRANTY; even without the implied
 WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public License along with this 
 program (see gnu-gplv2.txt). If not, write to the Free Software Foundation, Inc., 
 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or visit the Free Software
 Foundation web page, http://www.fsf.org.
 
 Created on: 01.02.2006
 *********************************************************************************/

package org.n52.oxf.feature.sos;

import java.io.InputStream;

import net.opengis.om.x10.ObservationCollectionDocument;
import net.opengis.om.x10.ObservationCollectionType;
import net.opengis.sos.x20.GetObservationResponseDocument;
import net.opengis.sos.x20.GetObservationResponseType;
import net.opengis.sos.x20.GetObservationResponseType.ObservationData;
import net.opengis.waterml.x20.TimeseriesObservationDocument;
import net.opengis.waterml.x20.TimeseriesObservationType;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.n52.oxf.OXFException;
import org.n52.oxf.feature.IFeatureStore;
import org.n52.oxf.feature.OXFFeatureCollection;
import org.n52.oxf.feature.OXFObservationCollectionType;
import org.n52.oxf.owsCommon.capabilities.Parameter;
import org.n52.oxf.serviceAdapters.OperationResult;
import org.n52.oxf.serviceAdapters.ParameterContainer;
import org.n52.oxf.serviceAdapters.ParameterShell;
import org.n52.oxf.serviceAdapters.sos.SOSAdapter;
import org.n52.oxf.util.LoggingHandler;

public class SOSObservationStore implements IFeatureStore {

    private static Logger LOGGER = LoggingHandler.getLogger(SOSObservationStore.class);
    private XmlObject xmlObject;
    private String version;
    
    @Deprecated
    public SOSObservationStore() {
        // TODO remove when removing deprecated #unmarshalFeatures100()
    }

    public SOSObservationStore(OperationResult operationResult) throws OXFException {
        this.xmlObject = parseToXmlObject(operationResult.getIncomingResultAsStream());
        this.version = getVersion(operationResult);
    }
    
    public OXFFeatureCollection unmarshalFeatures(OperationResult operationResult) throws OXFException {
        this.version = getVersion(operationResult);
        if (SOSAdapter.isVersion100(version)) {
            return unmarshalFeatures100(operationResult);
        } else {
            return unmarshalFeatures();
        }
    }
    
    public OXFFeatureCollection unmarshalFeatures() throws OXFException {
        if (SOSAdapter.isVersion100(version)) {
            return unmarshalFeatures100();
        } else if (SOSAdapter.isVersion200(version)) {
            return unmarshalFeatures200();
        } else {
            LOGGER.error("Cannot unmarshal FeatureCollection.");
            throw new OXFException(String.format("SOS version '%s' is not unsupported!", version));
        }
    }

    private String getVersion(OperationResult operationResult) {
        ParameterContainer parameters = operationResult.getUsedParameters();
        ParameterShell shell = parameters.getParameterShellWithCommonName(Parameter.COMMON_NAME_VERSION);
        return (String) shell.getSpecifiedValue();
    }

    /**
     * @deprecated use instead {@link #SOSObservationStore(OperationResult)} and {@link #unmarshalFeatures100()}
     */
    @Deprecated
    protected OXFFeatureCollection unmarshalFeatures100(OperationResult operationResult) throws OXFException {
        this.xmlObject = parseToXmlObject(operationResult.getIncomingResultAsStream());
        return unmarshalFeatures(operationResult);
    }
    
    private OXFFeatureCollection unmarshalFeatures100() throws OXFException {
        if (xmlObject == null) {
            // TODO remove when removing deprecated #unmarshalFeatures100()
            throw new IllegalStateException("Store was not initialized with an operationResult!");
        }
        try {
            OXFObservationCollectionType obsCollectionType = new OXFObservationCollectionType();
            if (isOM100ObservationCollectionDocument(xmlObject)) {
                net.opengis.om.x10.ObservationCollectionDocument obsCollectionDoc = (ObservationCollectionDocument) xmlObject;
                ObservationCollectionType obsCollection = obsCollectionDoc.getObservationCollection();
                String obsCollectionId = obsCollection.getId();
                OXFFeatureCollection featureCollection = new OXFFeatureCollection(obsCollectionId, obsCollectionType);
                obsCollectionType.initializeFeature(featureCollection, obsCollection);
                return featureCollection;
            } else if (isWaterML200TimeSeriesObservationDocument(xmlObject)) {
                net.opengis.waterml.x20.TimeseriesObservationDocument timeseriesObservationDoc = (TimeseriesObservationDocument) xmlObject;
                TimeseriesObservationType timeseriesObservation = timeseriesObservationDoc.getTimeseriesObservation();
                String timeseriesObservationId = timeseriesObservation.getId();
                OXFFeatureCollection featureCollection = new OXFFeatureCollection(timeseriesObservationId, obsCollectionType);
                obsCollectionType.initializeFeature(featureCollection, timeseriesObservation);
                return featureCollection;
            } else {
                throw new OXFException("Unknown result type.");
            }
        } catch (Exception e) {
            throw new OXFException(e);
        }
    }

    private XmlObject parseToXmlObject(InputStream in) throws OXFException {
        try {
            return XmlObject.Factory.parse(in);
        } catch (Exception e) {
            throw new OXFException("Cannot parse InputStream to XmlObject.", e);
        }
    }
    
    private boolean isOM100ObservationCollectionDocument(XmlObject xmlObject) {
        return xmlObject instanceof net.opengis.om.x10.ObservationCollectionDocument;
    }

    private boolean isWaterML200TimeSeriesObservationDocument(XmlObject xmlObject) {
        return xmlObject instanceof net.opengis.waterml.x20.TimeseriesObservationDocument;
    }
    
    private OXFFeatureCollection unmarshalFeatures200() throws OXFException {
        if (xmlObject == null) {
            // TODO remove when removing deprecated #unmarshalFeatures100()
            throw new IllegalStateException("Store was not initialized with an operationResult!");
        }
        try {
            OXFObservationCollectionType obsCollectionType = new OXFObservationCollectionType();
            if (isSOS200GetObservationResponseDocument(xmlObject)) {
                GetObservationResponseDocument observationResponseDocument = (GetObservationResponseDocument) xmlObject;
                GetObservationResponseType observationResponseType = observationResponseDocument.getGetObservationResponse();
                String noneSenseId = String.valueOf(System.currentTimeMillis()); // XXX no collection id available
                OXFFeatureCollection featureCollection = new OXFFeatureCollection(noneSenseId, obsCollectionType);
                ObservationData[] observationData = observationResponseType.getObservationDataArray();
                obsCollectionType.initializeFeature(featureCollection, observationData);
                return featureCollection;
            } else {
                throw new OXFException("Unknown result type.");
            }
        } catch (Exception e) {
            throw new OXFException(e);
        }
    }

    private boolean isSOS200GetObservationResponseDocument(XmlObject xmlObject) {
        return xmlObject instanceof GetObservationResponseDocument;
    }
    
    
}