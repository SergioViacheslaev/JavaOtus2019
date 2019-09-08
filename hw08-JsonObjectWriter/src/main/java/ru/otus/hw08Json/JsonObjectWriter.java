package ru.otus.hw08Json;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author Sergei Viacheslaev
 */
public class JsonObjectWriter {
    public String toJsonString(Object src) {
        if (src == null) {
            return null;
        }
        JsonValue jsonValue = createJsonValue(src.getClass(), src);
        return jsonValue == null ? null : jsonValue.toString();
    }

    private JsonValue createJsonValue(Class<?> type, Object obj) {
        boolean isNull = Objects.isNull(obj);

        if (isPrimitiveValue(type)) {
            return createPrimitiveValue(obj);
        } else if (type.isArray()) {
            return isNull ? JsonArray.EMPTY_JSON_ARRAY : createArrayValue(obj);
        } else if (Collection.class.isAssignableFrom(type)) {
            Collection collection = (Collection) obj;
            return isNull ? JsonArray.EMPTY_JSON_ARRAY : createArrayValue(collection.toArray());
        } else if (Map.class.isAssignableFrom(type)) {
            return isNull ? JsonValue.EMPTY_JSON_OBJECT : createMapValue(obj);
        } else {
            try {
                return createJsonObject(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return JsonValue.NULL;

    }


    private JsonObject createJsonObject(Object object) throws IllegalAccessException {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            Object objectValue = field.get(object);
            if (objectValue != null && isNotStaticOrTransient(modifiers)) {
                builder.add(field.getName(), createJsonValue(field.getType(), objectValue));
            }
        }

        return builder.build();

    }


    private JsonValue createMapValue(Object obj) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Map<?, ?> map = (Map<?, ?>) obj;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            objectBuilder.add(key.toString(), createJsonValue(value.getClass(), value));
        }
        return objectBuilder.build();
    }

    private JsonArray createArrayValue(Object obj) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(obj, i);
            arrayBuilder.add(createJsonValue(value.getClass(), value));
        }
        return arrayBuilder.build();
    }

    private JsonValue createPrimitiveValue(Object obj) {
        if (obj instanceof Number) {
            Number number = (Number) obj;
            if (number instanceof Double || number instanceof Float) {
                return Json.createValue(number.doubleValue());
            } else {
                return Json.createValue(number.longValue());
            }
        } else if (obj instanceof Boolean) {
            return obj.equals(true) ? JsonValue.TRUE : JsonValue.FALSE;
        } else {
            return Json.createValue(obj.toString());
        }
    }

    private boolean isPrimitiveValue(Class<?> clazz) {
        return clazz.isPrimitive() || String.class.isAssignableFrom(clazz) ||
                Number.class.isAssignableFrom(clazz) ||
                Boolean.class.isAssignableFrom(clazz) ||
                Character.class.isAssignableFrom(clazz);
    }

    private boolean isNotStaticOrTransient(int modifiers) {
        return !Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers);
    }
}
