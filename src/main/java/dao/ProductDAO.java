package dao;


import entity.Product;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductDAO {

    private final SessionFactory sessionFactory;
    public void create(Product product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Product> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("FROM Product p ORDER BY name", Product.class);
            return query.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteById(UUID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            MutationQuery mutationQuery = session.createMutationQuery("DELETE FROM Product p WHERE id = :id");
            mutationQuery.setParameter("id", id);
            mutationQuery.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
