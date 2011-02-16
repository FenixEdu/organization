package module.contacts.presentationTier.action.bean.addressbean;



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
