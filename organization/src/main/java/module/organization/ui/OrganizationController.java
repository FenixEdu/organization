package module.organization.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.domain.UserProfile;
import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.fenixedu.commons.i18n.I18N;
import org.fenixedu.commons.i18n.LocalizedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;

import module.organization.Utils;
import module.organization.domain.AccountabilityType;
import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import module.organization.domain.PartyType;
import module.organization.domain.Person;
import module.organization.domain.Unit;

@SpringApplication(group = "logged", path = "organization", title = "label.organization.module", hint = "Organization")
@SpringFunctionality(app = OrganizationController.class, title = "label.organization.module")
@RequestMapping("/organization")
public class OrganizationController {

    private final MessageSource messageSource;

    @Autowired
    public OrganizationController(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(final Model model) {
        final Stream<OrganizationalModel> models = OrganizationalModel.getModelStream();
        return returnCollection(model, "models", models, OrganizationalModel.COMPARATORY_BY_NAME, this::modelBasic, "organization/home");
    }
    
    @RequestMapping(value = "/partyType/list", method = RequestMethod.GET)
    public String partyTypes(final Model model) {
        final Stream<PartyType> partyTypes = OrganizationalModel.getAllPartyTypes();
        return returnCollection(model, "partyTypes", partyTypes, PartyType.COMPARATORY_BY_NAME, this::partyType, "organization/partyTypes");
    }

    @RequestMapping(value = "/accountabilityType/list", method = RequestMethod.GET)
    public String accountabilityType(final Model model) {
        final Stream<AccountabilityType> accountabilityTypes = OrganizationalModel.getAllAccountabilityTypes();
        return returnCollection(model, "accountabilityTypes", accountabilityTypes, AccountabilityType.COMPARATORY_BY_NAME,
                this::accountabilityType, "organization/accountabilityTypes");
    }

    private <T> String returnCollection(final Model model, final String attributeName, final Stream<T> stream,
            final Comparator<T> comparator, final BiConsumer<JsonObject, T> consumer, final String returnValue) {
        final Stream<T> models = stream.sorted(comparator.reversed());
        Utils.put(model, attributeName, models, consumer);
        return returnValue;
    }

    @RequestMapping(value = "/createNewModel", method = RequestMethod.POST)
    public String createNewModel(final Model model, final @RequestParam String name) {
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Model name cannot be null or empty.");
        }
        OrganizationalModel.create(new LocalizedString(I18N.getLocale(), name));
        return "redirect:/organization";
    }

    @RequestMapping(value = "/createNewPartyType", method = RequestMethod.POST)
    public String createNewPartyType(final Model model, final @RequestParam String type, final @RequestParam String name) {
        if (type == null || type.isEmpty()) {
            throw new NullPointerException("Type cannot be null or empty.");
        }
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Nameame cannot be null or empty.");
        }
        PartyType.create(type, new LocalizedString(I18N.getLocale(), name));
        return "redirect:/organization/partyType/list";
    }

    @RequestMapping(value = "/createNewAccountabilityType", method = RequestMethod.POST)
    public String createNewAccountabilityType(final Model model, final @RequestParam String type, final @RequestParam String name) {
        if (type == null || type.isEmpty()) {
            throw new NullPointerException("Type cannot be null or empty.");
        }
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Nameame cannot be null or empty.");
        }
        AccountabilityType.create(type, new LocalizedString(I18N.getLocale(), name));
        return "redirect:/organization/accountabilityType/list";
    }

    @RequestMapping(value = "/model/{organizationModel}", method = RequestMethod.GET)
    public String viewModel(final Model model, final @PathVariable OrganizationalModel organizationModel) {
        model.addAttribute("organizationModel", Utils.toJson(this::modelForStructure, organizationModel));
        return "organization/model";
    }

    @RequestMapping(value = "/model/{organizationModel}/configuration", method = RequestMethod.GET)
    public String accountabilityTypes(final Model model, final @PathVariable OrganizationalModel organizationModel) {
        model.addAttribute("organizationModel", Utils.toJson(this::modelForStructure, organizationModel));
        final Stream<AccountabilityType> accountabilityTypes = OrganizationalModel.getAllAccountabilityTypes();
        return returnCollection(model, "accountabilityTypes", accountabilityTypes, AccountabilityType.COMPARATORY_BY_NAME,
                this::accountabilityType, "organization/modelConfiguration");
    }

    @RequestMapping(value = "/model/{organizationModel}/configuration", method = RequestMethod.POST)
    public String configuration(final Model model, final @PathVariable OrganizationalModel organizationModel,
            final @RequestParam LocalizedString name, final @RequestParam AccountabilityType[] accountabilityTypes) {
        organizationModel.configure(name, Arrays.asList(accountabilityTypes));
        return "redirect:/organization/model/" + organizationModel.getExternalId();
    }

    @RequestMapping(value = "/partyType/{partyType}", method = RequestMethod.GET)
    public String viewPartyType(final Model model, final @PathVariable PartyType partyType) {
        model.addAttribute("partyType", Utils.toJson(this::partyType, partyType));
        return "organization/partyType";
    }

    @RequestMapping(value = "/accountabilityType/{accountabilityType}", method = RequestMethod.GET)
    public String viewAccountabilityType(final Model model, final @PathVariable AccountabilityType accountabilityType) {
        model.addAttribute("accountabilityType", Utils.toJson(this::accountabilityType, accountabilityType));
        return "organization/accountabilityType";
    }

    @RequestMapping(value = "/partyType/{partyType}", method = RequestMethod.POST)
    public String editPartyType(final Model model, final @PathVariable PartyType partyType, 
            final @RequestParam String type, final @RequestParam LocalizedString name) {
        partyType.edit(type, name);
        return "redirect:/organization/partyType/list";
    }

    @RequestMapping(value = "/accountabilityType/{accountabilityType}", method = RequestMethod.POST)
    public String editAccountabilityType(final Model model, final @PathVariable AccountabilityType accountabilityType, 
            final @RequestParam String type, final @RequestParam LocalizedString name) {
        accountabilityType.edit(type, name);
        return "redirect:/organization/accountabilityType/list";
    }

    @RequestMapping(value = "/partyType/{partyType}/delete", method = RequestMethod.POST)
    public String deletePartyType(final Model model, final @PathVariable PartyType partyType) {
        partyType.delete();
        return "redirect:/organization/partyType/list";
    }

    @RequestMapping(value = "/accountabilityType/{accountabilityType}/delete", method = RequestMethod.POST)
    public String deleteAccountabilityType(final Model model, final @PathVariable AccountabilityType accountabilityType) {
        accountabilityType.delete();
        return "redirect:/organization/accountabilityType/list";
    }

    private void modelBasic(final JsonObject result, final OrganizationalModel model) {
        result.addProperty("id", model.getExternalId());
        result.add("name", Utils.toJson(model.getName()));
    }

    private void modelForStructure(final JsonObject result, final OrganizationalModel model) {
        modelBasic(result, model);
        result.add("units", Utils.toJsonArray(model.getUnitStream(), this::unitBasic));
        result.add("people", Utils.toJsonArray(model.getPersonStream(), this::personBasic));
        result.add("accountabilityTypes", Utils.toJsonArray(model.getAccountabilityTypeStream(), this::accountabilityType));
    }

    private void partyBasic(final JsonObject result, final Party party) {
        result.addProperty("id", party.getExternalId());
        result.addProperty("name", party.getPartyName().getContent());
        result.addProperty("presentationName", party.getPresentationName());
    }

    private void unitBasic(final JsonObject result, final Unit unit) {
        partyBasic(result, unit);
        result.addProperty("acronym", unit.getAcronym());
    }

    private void personBasic(final JsonObject result, final Person person) {
        partyBasic(result, person);
        final User user = person.getUser();
        final UserProfile profile = user.getProfile();
        result.addProperty("avatarUrl", profile.getAvatarUrl());
        result.addProperty("relativePath", "/organization/person/" + person.getExternalId());
    }

    private void accountabilityType(final JsonObject result, final AccountabilityType type) {
        result.addProperty("id", type.getExternalId());
        result.addProperty("type", type.getType());
        result.add("name", Utils.toJson(type.getName()));
    }

    private void partyType(final JsonObject result, final PartyType type) {
        result.addProperty("id", type.getExternalId());
        result.addProperty("type", type.getType());
        result.add("name", Utils.toJson(type.getName()));        
    }
}
