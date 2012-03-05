/*
 * @(#)PhysicalAddressBeanInputRendererWithPostBack.java
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
package module.contacts.presentationTier.renderers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import module.contacts.presentationTier.action.bean.PhysicalAddressBean;
import module.geography.domain.Country;
import module.geography.domain.CountrySubdivision;
import module.geography.domain.CountrySubdivisionLevelName;
import module.geography.domain.GeographicLocation;
import myorg.domain.MyOrg;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.Address;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyConverter;
import pt.ist.fenixWebFramework.renderers.InputRenderer;
import pt.ist.fenixWebFramework.renderers.components.HtmlBlockContainer;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlHiddenField;
import pt.ist.fenixWebFramework.renderers.components.HtmlInlineContainer;
import pt.ist.fenixWebFramework.renderers.components.HtmlMenu;
import pt.ist.fenixWebFramework.renderers.components.HtmlMenuOption;
import pt.ist.fenixWebFramework.renderers.components.HtmlMultipleHiddenField;
import pt.ist.fenixWebFramework.renderers.components.HtmlSimpleValueComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlTable;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableCell;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableCell.CellType;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableRow;
import pt.ist.fenixWebFramework.renderers.components.HtmlText;
import pt.ist.fenixWebFramework.renderers.components.HtmlTextInput;
import pt.ist.fenixWebFramework.renderers.components.controllers.HtmlController;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;
import pt.ist.fenixWebFramework.renderers.components.state.IViewState;
import pt.ist.fenixWebFramework.renderers.components.state.ViewDestination;
import pt.ist.fenixWebFramework.renderers.converters.EnumConverter;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;
import pt.ist.fenixWebFramework.renderers.model.MetaSlot;
import pt.ist.fenixWebFramework.renderers.model.MetaSlotKey;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.renderers.validators.HtmlChainValidator;
import pt.ist.fenixWebFramework.renderers.validators.HtmlValidator;
import pt.utl.ist.fenix.tools.util.Pair;
import pt.utl.ist.fenix.tools.util.i18n.Language;

/**
 * 
 * @author João Antunes
 * 
 */
public class PhysicalAddressBeanInputRendererWithPostBack extends InputRenderer {

    private static final String HIDDEN_NAME = "postback";
    private String bundle;

    private String genericLabel1Key;

    private String genericLabel2Key;

    private String postalCodeKey;
    private String locationKey;
    private String countryKey;
    private String classes;
    private String errorClasses;

    private String labelsSize;
    private String labelsMaxLength;
    private String postalCodeSize;
    private String locationSize;
    private String countrySize;

    /**
     * If true, it wraps the countries that can't find in the user's native
     * language in ''. e.g. 'Afghanistan'
     */
    private boolean toWrapForeignLanguage;

    private String destination;

    @Override
    public String getClasses() {
	return classes;
    }

    @Override
    public void setClasses(String classes) {
	this.classes = classes;
    }

    public void setErrorClasses(String errorClasses) {
	this.errorClasses = errorClasses;
    }

    public String getErrorClasses() {
	return errorClasses;
    }

    public String getBundle() {
	return bundle;
    }

    public void setBundle(String bundle) {
	this.bundle = bundle;
    }


    public String getPostalCodeKey() {
	return postalCodeKey;
    }

    public void setPostalCodeKey(String postalCodeKey) {
	this.postalCodeKey = postalCodeKey;
    }

    public String getLocationKey() {
	return locationKey;
    }

    public void setLocationKey(String locationKey) {
	this.locationKey = locationKey;
    }

    public String getCountryKey() {
	return countryKey;
    }

    public void setCountryKey(String countryKey) {
	this.countryKey = countryKey;
    }

    public String getPostalCodeSize() {
	return postalCodeSize;
    }

    public void setPostalCodeSize(String postalCodeSize) {
	this.postalCodeSize = postalCodeSize;
    }

    public String getLocationSize() {
	return locationSize;
    }

    public void setLocationSize(String locationSize) {
	this.locationSize = locationSize;
    }

    public String getCountrySize() {
	return countrySize;
    }

    public void setCountrySize(String countrySize) {
	this.countrySize = countrySize;
    }

    @Override
    public HtmlComponent render(Object object, Class type) {
	HtmlInlineContainer container = new HtmlInlineContainer();

	String prefix = HtmlComponent.getValidIdOrName(((MetaSlot) getInputContext().getMetaObject()).getKey().toString()
		.replaceAll("\\.", "_").replaceAll("\\:", "_"));

	HtmlHiddenField hidden = new HtmlHiddenField(prefix + HIDDEN_NAME, "");

	// we will have a set of HtmlMenus on the rendered component, which we
	// will want to extract and disable depending on the hidden values
	HtmlBlockContainer renderedContainer = (HtmlBlockContainer) super.render(object, type);

	// menu.setOnChange("this.form." + prefix + HIDDEN_NAME +
	// ".value='true';this.form.submit();");
	// menu.setController(new PostBackController(hidden, getDestination()));

	container.addChild(hidden);
	// container.addChild(menu);

	return container;
    }

    public String getDestination() {
	return destination;
    }

    private static class PostBackController extends HtmlController {

	private final HtmlHiddenField hidden;

	private final String destination;

	public PostBackController(HtmlHiddenField hidden, String destination) {
	    this.hidden = hidden;
	    this.destination = destination;
	}

	@Override
	public void execute(IViewState viewState) {
	    if (hidden.getValue() != null && hidden.getValue().equalsIgnoreCase("true")) {
		String destinationName = this.destination == null ? "postBack" : this.destination;
		ViewDestination destination = viewState.getDestination(destinationName);

		if (destination != null) {
		    viewState.setCurrentDestination(destination);
		} else {
		    viewState.setCurrentDestination("postBack");
		}
		hidden.setValue("false");
		viewState.setSkipValidation(true);
	    }
	}
    }

    @Override
    protected Layout getLayout(Object arg0, Class arg1) {
	return new Layout() {

	    @Override
	    public HtmlComponent createComponent(Object object, Class arg1) {

		HtmlBlockContainer container = new HtmlBlockContainer();

		// assert the number of levels that we will have!
		PhysicalAddressBean physicalAddressBean = (PhysicalAddressBean) object;

		Country country = physicalAddressBean.getCountry();


		HtmlMultipleHiddenField htmlMultipleHiddenField = new HtmlMultipleHiddenField();
		MetaSlotKey key = (MetaSlotKey) getInputContext().getMetaObject().getKey();
		htmlMultipleHiddenField.setTargetSlot(key);
		htmlMultipleHiddenField.setConverter(new AddressConverter());
		container.addChild(htmlMultipleHiddenField);

		HtmlTable table = new HtmlTable();
		table.setClasses(getClasses());
		

		// get the country to assert how many levels we will have
		if (country.getSubdivisionDepth() == 0) {
		    ArrayList<HtmlTableCell> errorCells = new ArrayList<HtmlTableCell>(1);
		    // then we will have the generic label with address here
		    HtmlTableRow row = table.createRow();
		    String genericLabel1String = RenderUtils.getResourceString(getBundle(), getGenericLabel1Key());
		    row.createCell().setBody(new HtmlText(genericLabel1String));
		    addTextInputLabel(row, genericLabel1String, new Integer(1), errorCells);

		    HtmlTableRow row2 = table.createRow();
		    String genericLabel2String = RenderUtils.getResourceString(getBundle(), getGenericLabel2Key());
		    row.createCell().setBody(new HtmlText(genericLabel2String));
		    addTextInputLabel(row2, genericLabel2String, new Integer(1), errorCells);
		} else {
		    ArrayList<HtmlTableCell> errorCells = new ArrayList<HtmlTableCell>(country.getSubdivisionDepth());

		    // we will get the labels on the subdivision levels
		    ArrayList<CountrySubdivisionLevelName> countrySubDivisions = new ArrayList(country.getLevelName());
		    for (CountrySubdivisionLevelName countrySubdivisionLevelName : countrySubDivisions) {
			HtmlTableRow row = table.createRow();
			String levelName = countrySubdivisionLevelName.getName().getContent();
			row.createCell(CellType.HEADER).setBody(new HtmlText(levelName));
			/*
			 * if (countrySubdivisionLevelName.getIsLabel()) {
			 * addTextInputLabel(row, levelName,
			 * countrySubdivisionLevelName.getLevel(), errorCells);
			 * } else { // let's make a drop-down to select them
			 * HtmlMenu menu = new HtmlMenu(); // for each of the
			 * geographic locations on that // level, let's put them
			 * on the menu addMenuOptions(menu,
			 * countrySubdivisionLevelName);
			 * row.createCell().setBody(menu); }
			 */
		    }

		}

		htmlMultipleHiddenField.setConverter(new AddressConverter());
		// country.setController(new AddressController(line1, line2,
		// location, postalCode, country, htmlMultipleHiddenField));

		HtmlChainValidator htmlChainValidator = new HtmlChainValidator(htmlMultipleHiddenField);
		htmlMultipleHiddenField.setChainValidator(htmlChainValidator);
		new HtmlValidator(htmlChainValidator) {

		    List<String> errorMessages = new ArrayList<String>();

		    @Override
		    public void performValidation() {
			String[] values = getComponent().getValues();
			// if (values[0] == null || values[0].isEmpty()) {
			// HtmlInlineContainer htmlInlineContainer1 = new
			// HtmlInlineContainer();
			// htmlInlineContainer1.addChild(new
			// HtmlText(getResourceMessage("error.line1.cannot.be.empty")));
			// errorCells[0].setBody(htmlInlineContainer1);
			// errorMessages.add("error.line1.cannot.be.empty");
			// }
			// if (values[2] == null || values[2].isEmpty()) {
			// HtmlInlineContainer htmlInlineContainer2 = new
			// HtmlInlineContainer();
			// htmlInlineContainer2.addChild(new
			// HtmlText(getResourceMessage("error.location.cannot.be.empty")));
			// errorCells[2].setBody(htmlInlineContainer2);
			// errorMessages.add("error.location.cannot.be.empty");
			// }
			// if (values[3] == null || values[3].isEmpty()) {
			// HtmlInlineContainer htmlInlineContainer3 = new
			// HtmlInlineContainer();
			// htmlInlineContainer3.addChild(new
			// HtmlText(getResourceMessage("error.postalCode.cannot.be.empty")));
			// errorCells[3].setBody(htmlInlineContainer3);
			// errorMessages.add("error.postalCode.cannot.be.empty");
			// }
			// if (values[4] == null || values[4].isEmpty()) {
			// HtmlInlineContainer htmlInlineContainer4 = new
			// HtmlInlineContainer();
			// htmlInlineContainer4.addChild(new
			// HtmlText(getResourceMessage("error.country.cannot.be.empty")));
			// errorCells[4].setBody(htmlInlineContainer4);
			// errorMessages.add("error.country.cannot.be.empty");
			// }
			//
			// if (!errorMessages.isEmpty()) {
			// setValid(false);
			// }

		    }

		};

		 container.addChild(table);
		return container;
	    }

	    private void addMenuOptions(HtmlMenu menu, CountrySubdivisionLevelName countrySubdivisionLevelName) {
		// TODO Auto-generated method stub

	    }

	};
    }

    private static class AddressConverter extends Converter {

	@Override
	public Object convert(Class type, Object value) {
	    String[] values = (String[]) value;
	    if (hasAnyValue(values)) {
		try {
		    return new Address(values[0], values[1], values[3], values[2], values[4]);
		} catch (DomainException e) {
		    e.printStackTrace();
		    return null;
		}
	    }
	    return null;
	}

	private boolean hasAnyValue(String[] values) {
	    for (String string : values) {
		if (!StringUtils.isEmpty(string)) {
		    return true;
		}
	    }
	    return false;
	}
    }

    /**
     * Adds a TextInput with a label to the given row with the given labelName
     * 
     * @param row
     *            the row generated from an existing table
     * @param levelName
     *            the name of the label to add
     * @param labelLevel
     *            the int of the level
     * @param errorCells
     *            the ArrayList of HtmlTableCell that holds the error cells, to
     *            which an error cell will e added
     */
    private void addTextInputLabel(HtmlTableRow row, String labelString, Integer labelLevel, ArrayList<HtmlTableCell> errorCells) {

	    HtmlTextInput label = new HtmlTextInput();
	    label.setMaxLength(Integer.valueOf(getLabelsMaxLength()));
	    label.setSize(getLabelsSize());
	label.setName(labelString + "_level_"
 + labelLevel);
	    label.setValue(null);
	    row.createCell().setBody(label);

	    // error cells
	    HtmlTableCell errorCell = row.createCell();
	    errorCell.setClasses(getErrorClasses());
	    errorCells.add(errorCell);
    }

    public void setDestination(String destination) {
	this.destination = destination;
    }

    /*
     * public void setDefaultOptionHidden(boolean defaultOptionHidden) {
     * this.defaultOptionHidden = defaultOptionHidden; }
     * 
     * public boolean isDefaultOptionHidden() { return defaultOptionHidden; }
     */


    public void setLabelsMaxLength(String labelsMaxLength) {
	this.labelsMaxLength = labelsMaxLength;
    }

    public String getLabelsMaxLength() {
	return labelsMaxLength;
    }

    public void setLabelsSize(String labelsSize) {
	this.labelsSize = labelsSize;
    }

    public String getLabelsSize() {
	return labelsSize;
    }

    public void setGenericLabel1Key(String genericLabel1Key) {
	this.genericLabel1Key = genericLabel1Key;
    }

    public String getGenericLabel1Key() {
	return genericLabel1Key;
    }

    public void setGenericLabel2Key(String genericLabel2Key) {
	this.genericLabel2Key = genericLabel2Key;
    }

    public String getGenericLabel2Key() {
	return genericLabel2Key;
    }

    private static class PhysicalAddressConverter extends Converter {

	@Override
	public Object convert(Class type, Object value) {
	    String[] values = (String[]) value;
	    if (hasAnyValue(values)) {
		try {

		    return new Address(values[0], values[1], values[3], values[2], values[4]);
		} catch (DomainException e) {
		    e.printStackTrace();
		    return null;
		}
	    }
	    return null;
	}

	private boolean hasAnyValue(String[] values) {
	    for (String string : values) {
		if (!StringUtils.isEmpty(string)) {
		    return true;
		}
	    }
	    return false;
	}
    }

    /**
     * This Controller gets the hidden fields that will use to store the values
     * and then it will have methods to add the values to store
     * 
     * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
     * 
     */
    private class PhysicalAddressController extends HtmlController {
	private final HtmlMultipleHiddenField hiddenFields;

	private final ArrayList<HtmlTextInput> textInputs;

	public PhysicalAddressController(HtmlMultipleHiddenField htmlMultipleHiddenFields) {
	    super();
	    this.hiddenFields = htmlMultipleHiddenFields;
	    this.textInputs = new ArrayList<HtmlTextInput>();
	}

	public void addTextInput(HtmlTextInput textInput) {
	    textInputs.add(textInput);
	}

	@Override
	public void execute(IViewState viewState) {
	    // hiddenFields.setValues(new String[] {});
	    // hiddenFields.addValue(line1.getValue());
	    // hiddenFields.addValue(line2.getValue());
	    // hiddenFields.addValue(location.getValue());
	    // hiddenFields.addValue(postalCode.getValue());
	    // hiddenFields.addValue(country.getValue());
	}
    }
}
