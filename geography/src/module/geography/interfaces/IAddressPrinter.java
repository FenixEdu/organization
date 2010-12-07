/**
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
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 * 
 */
public interface IAddressPrinter {

    public String getFormatedAddress(String complementarAddress);

}
