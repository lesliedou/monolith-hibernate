package com.begcode.demo.hibernate.util;

import jakarta.persistence.Id;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JpaUtil {

    public static String getPkColumn(String className) {
        String pkColumn = null;
        try {
            Class<?> clazz = Class.forName(className);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Id.class) != null) {
                    pkColumn = field.getName();
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pkColumn;
    }

    public static Map<String, Object> toMap(Tuple tuple) {
        Map<String, Object> result = new HashMap<>(tuple.getElements().size());
        for (TupleElement<?> element : tuple.getElements()) {
            result.put(element.getAlias(), tuple.get(element.getAlias()));
        }
        return result;
    }
}
