package com.example.highcakes.util;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("unchecked")
public class SearchUtils {
    public static <T> List<T> searchEntities(JpaRepository<T, Long> repository, String param, String... fields) {
        if (param != null && !param.isEmpty()) {
            StringBuilder methodName = new StringBuilder("findAllBy");
            for (String field : fields) {
                methodName.append(field.substring(0, 1).toUpperCase()).append(field.substring(1)).append("ContainingIgnoreCaseAnd");
            }
            methodName = new StringBuilder(methodName.substring(0, methodName.length() - 4));

            try {
                Method method = repository.getClass().getMethod(methodName.toString(), String.class);
                return (List<T>) method.invoke(repository, param);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Ошибка при выполнении поиска");
            }
        } else {
            return repository.findAll();
        }
    }
}