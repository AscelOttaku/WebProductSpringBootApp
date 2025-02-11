package com.webStore.webStore.repository.impl;

import com.webStore.webStore.model.Product;
import com.webStore.webStore.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void createProduct(Product product) {
        em.persist(product);
    }

    @Override
    public void updateProduct(Product product) {
        em.merge(product);
    }

    @Override
    public void deleteProduct(Product product) {
        em.remove(product);
    }

    @Override
    public Product getProduct(long id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> getAllProducts() {
        return em.createQuery("from Product", Product.class).getResultList();
    }

    public void closEm() {
        em.close();
    }
}
