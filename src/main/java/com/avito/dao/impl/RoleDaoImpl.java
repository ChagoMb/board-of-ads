package com.avito.dao.impl;

import com.avito.dao.interfaces.RoleDao;
import com.avito.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao {
    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findRoleByName(String name) {
        try {
            return entityManager.createQuery("from Role where role = : name", Role.class)
                    .setParameter("role", name)
                    .getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }
}
