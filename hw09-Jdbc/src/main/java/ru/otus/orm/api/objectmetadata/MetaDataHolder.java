package ru.otus.orm.api.objectmetadata;

import ru.otus.orm.objectmetadata.ObjectMetaData;

/**
 * @author Sergei Viacheslaev
 */

public interface MetaDataHolder {
    void saveObjectMetadata(Class className);

    ObjectMetaData getObjectMetaData(Class className);
}
