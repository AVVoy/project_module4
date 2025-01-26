package dao;

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
public class UserDAO {

    private final SessionFactory sessionFactory;

    public void create(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User u ORDER BY login", User.class);
            return query.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteById(UUID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            MutationQuery mutationQuery = session.createMutationQuery("DELETE FROM User u WHERE id = :id");
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
    public User findById(UUID id) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            user = session.find(User.class, id);
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }

        return user;
    }
}
