package com.mdharr.livechat.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private User user;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("LiveChat");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        emf.close();
    }

    @BeforeEach
    void setUp() throws Exception {
        em = emf.createEntityManager();
        user = em.find(User.class, 1);
    }

    @AfterEach
    void tearDown() throws Exception {
        em.close();
        user = null;
    }

    @Test
    void test_User_entity_mapping() {
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }
}
