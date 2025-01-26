package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Product;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class ProductDAO extends BaseDAO<Product, UUID>{

    public ProductDAO(ObjectMapper objectMapper, File file) {
        super(objectMapper, file, new TypeReference<List<Product>>() {});
    }

    @Override
    protected UUID getId(Product product) {
        return product.getId();
    }
}
