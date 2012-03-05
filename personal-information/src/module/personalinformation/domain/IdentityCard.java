/*
 * @(#)IdentityCard.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Personal Information Module.
 *
 *   The Personal Information Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Personal Information Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Personal Information Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.personalinformation.domain;

import java.io.Serializable;

import myorg.domain.exceptions.DomainException;

import org.joda.time.LocalDate;

/**
 * 
 * @author João Figueiredo
 * 
 */
public class IdentityCard extends IdentityCard_Base {

    static public class IdentityCardBean extends IdentificationDocumentBean implements Serializable {

	private static final long serialVersionUID = 5921987218303711171L;

	private LocalDate emissionDate;
	private LocalDate expirationDate;
	private String emissionLocation;

	public LocalDate getEmissionDate() {
	    return emissionDate;
	}

	public void setEmissionDate(LocalDate emissionDate) {
	    this.emissionDate = emissionDate;
	}

	public LocalDate getExpirationDate() {
	    return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
	    this.expirationDate = expirationDate;
	}

	public String getEmissionLocation() {
	    return emissionLocation;
	}

	public void setEmissionLocation(String emissionLocation) {
	    this.emissionLocation = emissionLocation;
	}
    }

    private IdentityCard() {
	super();
    }

    IdentityCard(final PersonalInformation information, final IdentificationDocumentBean bean) {
	this();
	init(information, bean);
	init((IdentityCardBean) bean);
    }

    private void init(final IdentityCardBean bean) {
	if (bean.getEmissionDate() == null) {
	    throw new DomainException("error.IdentificationDocument.invalid.emission.date");
	}
	if (bean.getExpirationDate() == null) {
	    throw new DomainException("error.IdentificationDocument.invalid.expiration.date");
	}
	if (bean.getEmissionLocation() == null || bean.getEmissionLocation().isEmpty()) {
	    throw new DomainException("error.IdentificationDocument.invalid.emission.location");
	}
	setEmissionDate(bean.getEmissionDate());
	setExpirationDate(bean.getExpirationDate());
	setEmissionLocation(bean.getEmissionLocation());
    }

}
