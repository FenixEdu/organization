/*
 * @(#)IAddressPrinter.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Pedro Santos
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Geography Module.
 *
 *   The Geography Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Geography Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Geography Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.geography.interfaces;

/**
 * Interface that has the methods to be implemented by the several
 * AddressPrinters per country that should exist and be assigned to the
 * modules.geography.domain.Country field
 * 
 * TODO
 * 
 * @author João Antunes
 * 
 */
public interface IAddressPrinter {

    public String getFormatedAddress(String complementarAddress);

}
