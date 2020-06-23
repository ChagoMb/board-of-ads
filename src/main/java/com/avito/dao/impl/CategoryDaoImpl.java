package com.avito.dao.impl;

import com.avito.dao.interfaces.CategoryDao;
import com.avito.dao.interfaces.UserDao;
import com.avito.models.Category;
import com.avito.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category addCategory(Category category) {
        return entityManager.merge(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return entityManager.merge(category);
    }

    @Override
    public void deleteCategory(long id) {
        entityManager.createQuery("delete from Category where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
