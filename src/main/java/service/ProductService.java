package service;

import dao.ProductDAO;
import entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDao;

    public void createProduct(Product product) {
        productDao.save(product);
    }

    public List<Product> getAllProducts(){
        return productDao.findAll();
    }

    public void deleteProductById(UUID productId) {
        productDao.delete(productId);
    }
}
