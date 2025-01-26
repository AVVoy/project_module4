package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
public abstract class BaseDAO<ENTITY, ID > {

    private final ObjectMapper objectMapper;
    private final File file;

    private final TypeReference<List<ENTITY>> typeReferenceListEntity;

    public void save(ENTITY entity) {
        try {
            List<ENTITY> entities = objectMapper.readValue(file, typeReferenceListEntity);
            entities.add(entity);

            objectMapper.writeValue(file, entities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ENTITY> findAll() {
        try {
            return objectMapper.readValue(file, typeReferenceListEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(ID id) {
        try {
            List<ENTITY> products = findAll();

            List<ENTITY> productListWithoutDeletedElement = products.stream()
                    .filter(entity -> !getId(entity).equals(id))
                    .toList();

            objectMapper.writeValue(file, productListWithoutDeletedElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract ID getId(ENTITY entity);
}
