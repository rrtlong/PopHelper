package com.moli.module.net.encryp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

/**
 * @author zixiaojun
 * @date 2018/4/25
 * @copyright Moli Teams
 */
public class ReflectKit {

    private ReflectKit() {
    }

    public static DigestRule reflectDigestRule(DigestRule digestRule) {
        try {
            if (digestRule != null) {
                Class<DigestRule> tClass = (Class<DigestRule>) digestRule.getClass();
                Field[] fields = getAllFields(tClass);
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object obj = field.get(digestRule);
                    if (obj == null) {
                        field.set(digestRule, null);
                    }
                    if (!isEncryptField(field)) {
                        field.set(digestRule, null);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            Timber.e(e);
        } catch (Exception e) {
            Timber.e(e);
        }

        return digestRule;
    }

    private static boolean isEncryptField(Field field) {
        boolean hasAnnotation = field.isAnnotationPresent(Encrypt.class);
        if (hasAnnotation) {
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            return encrypt == null || encrypt.required();
        }
        return true;
    }

    private static Field[] getAllFields(Class<?> cls) {
        List<Field> allFieldsList = getAllFieldsList(cls);
        return allFieldsList.toArray(new Field[allFieldsList.size()]);
    }

    private static List<Field> getAllFieldsList(Class<?> cls) {
        List<Field> allFields = new ArrayList<>();

        for (Class currentClass = cls; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            int len$ = declaredFields.length;

            allFields.addAll(Arrays.asList(declaredFields).subList(0, len$));
        }

        return allFields;
    }
}
