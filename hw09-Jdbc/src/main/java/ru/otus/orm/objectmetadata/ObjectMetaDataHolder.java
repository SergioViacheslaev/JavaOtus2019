package ru.otus.orm.objectmetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.annotations.Id;
import ru.otus.orm.api.objectmetadata.MetaDataHolder;
import ru.otus.orm.jdbc.dbexecutor.DbExecutor;
import ru.otus.orm.jdbc.dbexecutor.exceptions.DbExecutorException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class ObjectMetaDataHolder<T> implements MetaDataHolder {
    private static Logger logger = LoggerFactory.getLogger(ObjectMetaDataHolder.class);
    private Map<Class, ObjectMetaData> metaDataMap = new HashMap<>();

    public ObjectMetaDataHolder(Class<T> clazz) {
        saveObjectMetadata(clazz);
        logger.info("Object '{}' metadata is successfully saved.", clazz.getSimpleName());

    }

    @Override
    public void saveObjectMetadata(Class clazz) {
        ObjectMetaData metaData = new ObjectMetaData();
        List<Field> notIdFields = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        metaData.setTableName(clazz.getSimpleName());

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                metaData.setIdField(field);
            } else {
                notIdFields.add(field);
                columnNames.add(field.getName());

            }
        }

        if (Objects.isNull(metaData.getIdField())) throw new DbExecutorException("Object has no 'Id' annotation !");

        metaData.setNotIdFields(notIdFields);
        metaData.setColumnNames(columnNames);
        metaData.setSqlSelect(String.format("SELECT * FROM %s WHERE %s = ?", metaData.getTableName(), metaData.getIdFieldName()));
        metaData.setSqlInsert(String.format("insert into %s(%s,%s) values (?,?)", metaData.getTableName(), columnNames.get(0), columnNames.get(1)));
        metaData.setSqlUpdate(String.format("update %s set %s = ?, %s = ? where %s= ?", metaData.getTableName(),
                columnNames.get(0), columnNames.get(1), metaData.getIdFieldName()));

        metaDataMap.put(clazz, metaData);
    }

    @Override
    public ObjectMetaData getObjectMetaData(Class className) {
        return metaDataMap.get(className);
    }
}
