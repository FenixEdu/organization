package module.organization;

import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Stream;

import org.fenixedu.commons.i18n.LocalizedString;
import org.springframework.ui.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Utils {

    public static <T> JsonObject toJson(final BiConsumer<JsonObject, T> consumer, final T t) {
        final JsonObject result = new JsonObject();
        consumer.accept(result, t);
        return result;
    }

    public static <T> JsonArray toJsonArray(final Stream<T> stream, BiConsumer<JsonObject, T> consumer) {
        return stream.map(t -> toJson(consumer, t)).collect(Utils.toJsonArray());
    }

    public static <T extends JsonElement> Collector<T, JsonArray, JsonArray> toJsonArray() {
        return Collector.of(JsonArray::new, (array, element) -> array.add(element), (one, other) -> {
            one.addAll(other);
            return one;
        }, Characteristics.IDENTITY_FINISH);
    }

    public static <T> void put(final Model model, final String attributeName, final Stream<T> stream, BiConsumer<JsonObject, T> consumer) {
        model.addAttribute(attributeName, toJsonArray(stream, consumer));
    }

    public static JsonObject toJson(final LocalizedString lString) {
        if (lString == null) {
            return null;
        }
        final JsonObject result = new JsonObject();
        lString.forEach((l,s) -> result.addProperty(l.getLanguage(), s));
        return result;
    }

}
