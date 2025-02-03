package com.example.taskmanager.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UsersEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testUserEntityCanBePersisted() {

        Users user = new Users("testUser", "test@example.com", "password123");
        entityManager.persist(user);
        entityManager.flush();

        assertEquals(user.getUsername(), "testUser");
        assertEquals(user.getEmail(), "test@example.com");
    }

    @Test
    public void testUserEntityNullUsername() {
        Users user = new Users(null, "testing123@example", "password");

        assertThrows(PropertyValueException.class, () -> {
            entityManager.persist(user);
            entityManager.flush();
        });
    }

    @Test
    public void testUserEntityNullPassword() {
        Users user = new Users("user", "testing123@example.com", null);

        assertThrows(PropertyValueException.class, () -> {
            entityManager.persist(user);
            entityManager.flush();
        });
    }

    @Test
    public void testUserEntityNullEmail() {
        Users user = new Users("user", null, "password");

        assertThrows(PropertyValueException.class, () -> {
            entityManager.persist(user);
            entityManager.flush();
        });
    }

    @Test
    public void testUserEntityDuplicateEmail() {
        Users user1 = new Users("user1", "duplicate@example.com", "password123");
        Users user2 = new Users("user2", "duplicate@example.com", "anotherPassword");

        entityManager.persist(user1);
        entityManager.flush();

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(user2);
            entityManager.flush();
        });
    }


    @Test
    public void testUserEntityDuplicateUsername() {
        Users user1 = new Users("duplicate", "testing123@example.com", "password123");
        Users user2 = new Users("duplicate", "testing321@example.com", "anotherPassword");

        entityManager.persist(user1);
        entityManager.flush();

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(user2);
            entityManager.flush();
        });
    }





}
