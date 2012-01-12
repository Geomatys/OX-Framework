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
 
 Created on: 06.01.2006
 *********************************************************************************/

package org.n52.oxf.serviceAdapters.sos;

import net.opengis.fes.x20.BinaryTemporalOpType;
import net.opengis.fes.x20.DuringDocument;
import net.opengis.fes.x20.TEqualsDocument;
import net.opengis.gml.x32.TimeInstantType;
import net.opengis.gml.x32.TimePeriodType;
import net.opengis.gml.x32.TimePositionType;
import net.opengis.ows.x11.AcceptVersionsType;
import net.opengis.ows.x11.SectionsType;
import net.opengis.sos.x20.GetCapabilitiesDocument;
import net.opengis.sos.x20.GetCapabilitiesType;
import net.opengis.sos.x20.GetObservationDocument;
import net.opengis.sos.x20.GetObservationType;
import net.opengis.sos.x20.GetObservationType.TemporalFilter;
import net.opengis.swes.x20.DescribeSensorDocument;
import net.opengis.swes.x20.DescribeSensorType;

import org.apache.commons.lang.NotImplementedException;
import org.jfree.util.Log;
import org.n52.oxf.OXFException;
import org.n52.oxf.owsCommon.capabilities.ITime;
import org.n52.oxf.owsCommon.capabilities.Parameter;
import org.n52.oxf.serviceAdapters.ParameterContainer;
import org.n52.oxf.serviceAdapters.ParameterShell;
import org.n52.oxf.util.XmlBeansHelper;
import org.n52.oxf.valueDomains.time.ITimePeriod;
import org.n52.oxf.valueDomains.time.ITimePosition;
import org.n52.oxf.valueDomains.time.TimeFactory;

/**
 * contains attributes and methods to encode SOSOperationRequests as String in xml-format
 * 
 * @author <a href="mailto:broering@52north.org">Arne Broering</a>
 * @author <a href="mailto:ehjuerrens@52north.org">Eike Hinderk J&uuml;rrens</a>
 */
public class SOSRequestBuilder_200 implements ISOSRequestBuilder {

    public String buildGetCapabilitiesRequest(ParameterContainer parameters) {
        
        // FIXME not tested yet
        GetCapabilitiesDocument getCapDoc = GetCapabilitiesDocument.Factory.newInstance();
        GetCapabilitiesType getCap = getCapDoc.addNewGetCapabilities2();

        //
        // set required elements:
        //  

        getCap.setService((String) parameters.getParameterShellWithServiceSidedName(GET_CAPABILITIES_SERVICE_PARAMETER).getSpecifiedValue());

        //
        // set optional elements:
        //

        // Parameter "updateSequence":
        if (parameters.getParameterShellWithServiceSidedName(GET_CAPABILITIES_UPDATE_SEQUENCE_PARAMETER) != null) {
            getCap.setUpdateSequence((String) parameters.getParameterShellWithServiceSidedName(GET_CAPABILITIES_UPDATE_SEQUENCE_PARAMETER).getSpecifiedValue());
        }

        // Parameter "AcceptVersions":
        ParameterShell versionPS = parameters.getParameterShellWithServiceSidedName(GET_CAPABILITIES_ACCEPT_VERSIONS_PARAMETER);
        if (versionPS == null){
            versionPS = parameters.getParameterShellWithCommonName(Parameter.COMMON_NAME_VERSION);
        }
        if (versionPS != null) {
            AcceptVersionsType acceptedVersions = getCap.addNewAcceptVersions();
            
            if (versionPS.hasSingleSpecifiedValue()) {
                acceptedVersions.addVersion((String) versionPS.getSpecifiedValue());
            }
            else {
                Object[] versionArray = versionPS.getSpecifiedValueArray();
                for (Object version : versionArray) {
                    acceptedVersions.addVersion((String) version);
                }
            }
        }

        // Parameter "sections":
        ParameterShell sectionParamShell = parameters.getParameterShellWithServiceSidedName(GET_CAPABILITIES_SECTIONS_PARAMETER);
        if (sectionParamShell != null) {
            SectionsType sections = getCap.addNewSections();

            if (sectionParamShell.hasMultipleSpecifiedValues()) {
                String[] selectedSections = (String[]) sectionParamShell.getSpecifiedValueArray();
                for (int i = 0; i < selectedSections.length; i++) {
                    sections.addSection(selectedSections[i]);
                }
            }
            else if (sectionParamShell.hasSingleSpecifiedValue()) {
                sections.addSection((String) sectionParamShell.getSpecifiedValue());
            }
        }

        return XmlBeansHelper.formatStringRequest(getCapDoc);
    }

    public String buildGetObservationRequest(ParameterContainer parameters) throws OXFException {
        GetObservationDocument xb_getObsDoc = GetObservationDocument.Factory.newInstance();
        GetObservationType xb_getObs = xb_getObsDoc.addNewGetObservation();
        xb_getObs.setService((String) getShellForServerParameter(parameters, GET_OBSERVATION_SERVICE_PARAMETER).getSpecifiedValue());
        xb_getObs.setVersion((String) getShellForServerParameter(parameters, GET_OBSERVATION_VERSION_PARAMETER).getSpecifiedValue());
        processOffering(xb_getObs, getShellForServerParameter(parameters, GET_OBSERVATION_OFFERING_PARAMETER));
        processResponseFormat(xb_getObs, getShellForServerParameter(parameters, GET_OBSERVATION_RESPONSE_FORMAT_PARAMETER));
        processObservedProperty(xb_getObs, getShellForServerParameter(parameters, GET_OBSERVATION_OBSERVED_PROPERTY_PARAMETER));
        processTemporalFilter(xb_getObs, getShellForServerParameter(parameters, GET_OBSERVATION_TEMPORAL_FILTER_PARAMETER));
        processProcedure(xb_getObs, getShellForServerParameter(parameters, GET_OBSERVATION_PROCEDURE_PARAMETER));
        processFeatureOfInterest(xb_getObs, getShellForServerParameter(parameters, GET_OBSERVATION_FEATURE_OF_INTEREST_PARAMETER));
        processSpatialFilter(xb_getObs, getShellForServerParameter(parameters, GET_OBSERVATION_SPATIAL_FILTER_PARAMETER));
        return XmlBeansHelper.formatStringRequest(xb_getObsDoc);
    }
    
    private ParameterShell getShellForServerParameter(ParameterContainer container, String name) {
        return container.getParameterShellWithServiceSidedName(name);
    }

    private void processSpatialFilter(GetObservationType xb_getObs, ParameterShell shell) {
        if (shell == null) {
            return; // optional parameter
        }
        // TODO implement
        throw new NotImplementedException();
    }

    private void processFeatureOfInterest(GetObservationType xb_getObs, ParameterShell shell) {
        if (shell == null) {
            return; // optional parameter
        }
        ParameterShell foiParamShell = shell;
        if (foiParamShell.hasMultipleSpecifiedValues()) {
            Object[] fois = foiParamShell.getSpecifiedValueArray();
            xb_getObs.setFeatureOfInterestArray((String[]) fois);
        }
        else {
            Object foi = foiParamShell.getSpecifiedValue();
            xb_getObs.addNewFeatureOfInterest().setStringValue((String) foi);
        }
    }

    protected void processOffering(GetObservationType xb_getObs, ParameterShell shell) {
        if (shell != null) { // optional parameter
            xb_getObs.setOfferingArray((String[]) shell.getSpecifiedValueArray());
        }
    }

    protected void processResponseFormat(GetObservationType xb_getObs, ParameterShell shell) {
        if (shell != null) {
            xb_getObs.setResponseFormat((String) shell.getSpecifiedValue());
        }
    }

    protected void processProcedure(GetObservationType xb_getObs, ParameterShell shell) {
        if (shell != null) { // optional parameter
            xb_getObs.setProcedureArray((String[]) shell.getSpecifiedValueArray());
        }
    }
    
    protected void processObservedProperty(GetObservationType xb_getObs, ParameterShell shell) {
        if (shell == null) {
            return; // optional parameter
        }
        if (shell.hasMultipleSpecifiedValues()) {
            Object[] observedProperties = shell.getSpecifiedValueArray();
            xb_getObs.setObservedPropertyArray((String[]) observedProperties);
        }
        else if (shell.hasSingleSpecifiedValue()) {
            String observedProperty = (String) shell.getSpecifiedValue();
            xb_getObs.setObservedPropertyArray(new String[] {observedProperty});
        }
    }

    protected void processTemporalFilter(GetObservationType xb_getObs, ParameterShell shell) throws OXFException {
        if (shell == null) {
            return; // optional parameter
        }
        
        ITime specifiedTime;
        Object timeParamValue = shell.getSpecifiedValue();
        if (timeParamValue instanceof ITime) {
            specifiedTime = (ITime) timeParamValue;
        }
        else if (timeParamValue instanceof String) {
            specifiedTime = TimeFactory.createTime((String) timeParamValue);
        }
        else {
            throw new OXFException("The class (" + timeParamValue.getClass()
                    + ") of the value of the parameter 'eventTime' is not supported.");
        }

//        String timeType = null;
        BinaryTemporalOpType xb_binTempOp = null;
        if (specifiedTime instanceof ITimePeriod) {
            ITimePeriod oc_timePeriod = (ITimePeriod) specifiedTime;
            TimePeriodType xb_timePeriod = TimePeriodType.Factory.newInstance();
            DuringDocument duringDoc = DuringDocument.Factory.newInstance();
            xb_binTempOp = duringDoc.addNewDuring();
            
            TimePositionType xb_beginPosition = xb_timePeriod.addNewBeginPosition();
            TimePositionType xb_endPosition = xb_timePeriod.addNewEndPosition();
            xb_beginPosition.setStringValue(oc_timePeriod.getStart().toISO8601Format());
            xb_endPosition.setStringValue(oc_timePeriod.getEnd().toISO8601Format());
            
            xb_binTempOp.setValueReference("phenomenonTime"); // TODO always true?
            xb_binTempOp.set(xb_timePeriod);
//            timeType = "TimePeriod";
        }
        else if (specifiedTime instanceof ITimePosition) {
            ITimePosition oc_timePosition = (ITimePosition) specifiedTime;
            TimeInstantType xb_timeInstant = TimeInstantType.Factory.newInstance();
            TEqualsDocument equalsDoc = TEqualsDocument.Factory.newInstance();
            xb_binTempOp = equalsDoc.addNewTEquals();
            
            TimePositionType xb_timePosition = TimePositionType.Factory.newInstance();
            xb_timePosition.setStringValue(oc_timePosition.toISO8601Format());
            xb_timeInstant.setTimePosition(xb_timePosition);

            xb_binTempOp.setValueReference("phenomenonTime"); // TODO always true?
            xb_binTempOp.set(xb_timeInstant);
//            timeType = "TimePosition";
        }

        TemporalFilter spatialFilter = xb_getObs.addNewTemporalFilter();
        spatialFilter.setTemporalOps(xb_binTempOp);
        
//        // rename elements: FIXME still needed?
//        cursor = eventTime.newCursor();
//        cursor.toChild(new QName("http://www.opengis.net/ogc", "temporalOps"));
//        cursor.setName(new QName("http://www.opengis.net/ogc", "TM_Equals"));
//
//        cursor.toChild(new QName("http://www.opengis.net/gml", "_TimeObject"));
//        cursor.setName(new QName("http://www.opengis.net/gml", timeType));
    }
    
    
    public String buildGetObservationByIDRequest(ParameterContainer parameters) throws OXFException {
        throw new NotImplementedException("SOS Specification 2.0 not officially supported by now.");
    }

    public String buildDescribeSensorRequest(ParameterContainer parameters) {
        DescribeSensorDocument descSensorDoc = DescribeSensorDocument.Factory.newInstance();
        DescribeSensorType descSensor = descSensorDoc.addNewDescribeSensor();

        // set required elements:

        descSensor.setService((String) parameters.getParameterShellWithServiceSidedName(DESCRIBE_SENSOR_SERVICE_PARAMETER).getSpecifiedValue());
        descSensor.setVersion((String) parameters.getParameterShellWithServiceSidedName(DESCRIBE_SENSOR_VERSION_PARAMETER).getSpecifiedValue());
        processProcedure(descSensor, getShellForServerParameter(parameters, DESCRIBE_SENSOR_PROCEDURE_PARAMETER));
        processProcedureDescriptionFormat(descSensor, getShellForServerParameter(parameters, DESCRIBE_SENSOR_PROCEDURE_DESCRIPTION_FORMAT));
        
        
        return XmlBeansHelper.formatStringRequest(descSensorDoc);
    }
    
    protected void processProcedureDescriptionFormat(DescribeSensorType descSensor, ParameterShell shell) {
        if (shell == null) {
            Log.error("Missing shell parameter '" + DESCRIBE_SENSOR_PROCEDURE_DESCRIPTION_FORMAT + "'.");
            return; // throwing OXFException would break interface
        }
        descSensor.setProcedureDescriptionFormat((String) shell.getSpecifiedValue());
    }

    private void processProcedure(DescribeSensorType xb_descSensor, ParameterShell shell) {
        if (shell == null) {
            xb_descSensor.setProcedureDescriptionFormat("http://www.opengis.net/sensorml/1.0.1");
        } else {
            xb_descSensor.setProcedure((String) shell.getSpecifiedValue());
        }
    }

    public String buildGetFeatureOfInterestRequest(ParameterContainer parameters) {
        // TODO implement
        throw new NotImplementedException();
    }

    public String buildInsertObservation(ParameterContainer parameters) {
        // TODO implement
    	throw new NotImplementedException();
    }

    /**
     * Builds a RegisterSensor request and returns it.
     * A SensorML file can either be passed along or a set of parameters is used to create one.
     */
    public String buildRegisterSensor(ParameterContainer parameters) throws OXFException {
        // TODO implement
        throw new NotImplementedException();
    }	
    
}