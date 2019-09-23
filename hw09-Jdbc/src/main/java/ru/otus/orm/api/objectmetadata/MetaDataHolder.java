package ru.otus.orm.api.objectmetadata;

import ru.otus.orm.objectmetadata.ObjectMetaData;

/**
 * @author Sergei Viacheslaev
 */

public interface MetaDataHolder<T> {
    void saveObjectMetadata(Class<T> className);

    ObjectMetaData getObjectMetaData(Class<T> className);
}
