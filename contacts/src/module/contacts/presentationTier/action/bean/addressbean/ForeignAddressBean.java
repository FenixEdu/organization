/*
 * @(#)ForeignAddressBean.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: João Antunes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contacts Module.
 *
 *   The Contacts Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Contacts Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contacts Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contacts.presentationTier.action.bean.addressbean;



/**
 * 
 * @author João Antunes
 * 
 */
public class ForeignAddressBean extends CommonAddressBean {
    private String county;
    private String postCode;


    /**
     * @return the county
     */
    public String getCounty() {
	return county;
    }

    /**
     * @param county
     *            the county to set
     */
    public void setCounty(String county) {
	this.county = county;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
	return postCode;
    }

	/**
     * @param postCode
     *            the postCode to set
     */
    public void setPostCode(String postCode) {
	this.postCode = postCode;
    }

    @Override
    public String getComplementarAddress() {
	String complementarAddress = getAddressLineOne() + "\n" + getAddressLineTwo() + "\n" + getCounty() + "\n" + getCity()
		+ " " + getPostCode()
		+ " ";
	return complementarAddress;
    }


    @Override
    public boolean isValid() {
	// We can't validate anything, so we'll say it is valid
	return true;
    }
}
