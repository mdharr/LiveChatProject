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

class RoomTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private Room room;

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
        room = em.find(Room.class, 1);
    }

    @AfterEach
    void tearDown() throws Exception {
        em.close();
        room = null;
    }

    @Test
    void test_User_entity_mapping() {
        assertNotNull(room);
        assertEquals("Wombat World", room.getName());
    }
}
