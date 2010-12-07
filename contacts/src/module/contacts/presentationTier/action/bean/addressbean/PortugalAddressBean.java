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

}
