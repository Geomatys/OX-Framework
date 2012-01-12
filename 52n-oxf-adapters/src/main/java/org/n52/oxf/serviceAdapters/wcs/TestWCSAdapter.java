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
 
 Created on: 28.02.2008
 *********************************************************************************/

package org.n52.oxf.serviceAdapters.wcs;

import org.apache.log4j.Logger;
import org.n52.oxf.OXFException;
import org.n52.oxf.owsCommon.ExceptionReport;
import org.n52.oxf.owsCommon.ServiceDescriptor;
import org.n52.oxf.serviceAdapters.wcs.caps.CoverageDataset;
import org.n52.oxf.util.LoggingHandler;

/**
 * This class demonstrates how to use the WCSAdapter. You might use it as an example for your own code.
 * 
 * @author <a href="mailto:broering@52north.org">Arne Broering</a>
 * 
 */
public class TestWCSAdapter {

    private static Logger LOGGER = LoggingHandler.getLogger(TestWCSAdapter.class);

    private final String url = "http://ogcdemo.pcigeomatics.com:8181/swe/wcs";

    public static void main(String[] args) throws OXFException, ExceptionReport {
        new TestWCSAdapter().testServiceInitialization();
        
        javax.xml.bind.annotation.XmlAccessorType s;
    }

    public void testServiceInitialization() throws ExceptionReport, OXFException {

        WCSAdapter adapter = new WCSAdapter();

        ServiceDescriptor desc = adapter.initService(url);

        CoverageDataset coverage = (CoverageDataset) desc.getContents().getDataIdentification(0);

        LOGGER.info(coverage.getTitle());
    }

}