/**
 * 
 */
package module.geography.util;

import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * @author João André Pereira Antunes (joao.antunes@tagus.ist.utl.pt)
 *
 */
public class StringsUtil {

    public static MultiLanguageString makeName(String pt, String en) {
	MultiLanguageString name = new MultiLanguageString();
	name.setContent(Language.pt, pt);
	name.setContent(Language.en, en);
	return name;
    }

}
