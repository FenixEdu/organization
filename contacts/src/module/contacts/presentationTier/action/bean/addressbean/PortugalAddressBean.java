package module.contacts.presentationTier.action.bean.addressbean;

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
