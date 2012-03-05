/*
 * @(#)PortugalAddressBean.java
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
public class PortugalAddressBean extends CommonAddressBean {

    private Integer mainPostCode;
    private Integer postCodeExtension;

    /**
     * @return the mainPostCode
     */
    public Integer getMainPostCode() {
	return mainPostCode;
    }

    /**
     * @param mainPostCode
     *            the mainPostCode to set
     */
    public void setMainPostCode(Integer mainPostCode) {
	this.mainPostCode = mainPostCode;
    }

    /**
     * @return the postCodeExtension
     */
    public Integer getPostCodeExtension() {
	return postCodeExtension;
    }

    /**
     * @param postCodeExtension
     *            the postCodeExtension to set
     */
    public void setPostCodeExtension(Integer postCodeExtension) {
	this.postCodeExtension = postCodeExtension;
    }

    @Override
    public String getComplementarAddress() {
	String complementarAddress = getAddressLineOne() + "\n";
	if (getAddressLineTwo() != null && !getAddressLineTwo().isEmpty())
	    complementarAddress = complementarAddress.concat(getAddressLineTwo() + "\n");

	if (getMainPostCode() != null && getMainPostCode().compareTo(Integer.valueOf(0)) != 0) {
	    complementarAddress = complementarAddress.concat(getMainPostCode().toString());
	    if (getPostCodeExtension() != null && getPostCodeExtension().compareTo(Integer.valueOf(0)) != 0) {
		//if we also have an extended post code extension, let's add the -and it, if not
		complementarAddress = complementarAddress.concat("-" + getPostCodeExtension());
	    }
	    //now let's see if we add an \n or the county
	    if (getCity() != null & !getCity().isEmpty()) {
		complementarAddress = complementarAddress.concat(" " + getCity());
	    }
	    complementarAddress = complementarAddress.concat("\n");
	}
	complementarAddress = complementarAddress.concat(getGeographicLocation().getCountry() + "\n");


	return complementarAddress;
    }

    @Override
    public boolean isValid() {
	// We can't validate anything, so we'll say it is valid
	return true;
    }

}
