package ru.otus.orm.objectmetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.annotations.Id;
import ru.otus.orm.api.objectmetadata.MetaDataHolder;
import ru.otus.orm.jdbc.dbexecutor.exceptions.DbExecutorException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class ObjectMetaDataHolder<T> implements MetaDataHolder<T> {
    private static Logger logger = LoggerFactory.getLogger(ObjectMetaDataHolder.class);

    private Map<Class, ObjectMetaData> metaDataMap = new HashMap<>();

    public ObjectMetaDataHolder(Class<T> clazz) {
        saveObjectMetadata(clazz);
        logger.info("'{}' metadata is successfully saved.", clazz.getSimpleName());
    }


    @Override
    public void saveObjectMetadata(Class<T> clazz) {
        ObjectMetaData metaData = new ObjectMetaData();
        List<Field> notIdFields = new ArrayList<>();
        List<Field> allDeclaredFields = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        metaData.setTableName(clazz.getSimpleName());

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            allDeclaredFields.add(field);
            if (field.isAnnotationPresent(Id.class)) {
                metaData.setIdField(field);
            } else {
                notIdFields.add(field);
                columnNames.add(field.getName());

            }
        }

        if (Objects.isNull(metaData.getIdField())) throw new DbExecutorException("Object has no 'Id' annotation !");


        try {
            metaData.setEntityConstructor(clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        metaData.setNotIdFields(notIdFields);
        metaData.setAllDeclaredFields(allDeclaredFields);
        metaData.setSqlSelect(String.format("SELECT * FROM %s WHERE %s = ?", metaData.getTableName(), metaData.getIdFieldName()));
        metaData.setSqlInsert(String.format("insert into %s(%s,%s) values (?,?)", metaData.getTableName(), columnNames.get(0), columnNames.get(1)));
        metaData.setSqlUpdate(String.format("update %s set %s = ?, %s = ? where %s= ?", metaData.getTableName(),
                columnNames.get(0), columnNames.get(1), metaData.getIdFieldName()));

        metaDataMap.put(clazz, metaData);
    }

    @Override
    public ObjectMetaData getObjectMetaData(Class<T> className) {
        return metaDataMap.get(className);
    }
}
