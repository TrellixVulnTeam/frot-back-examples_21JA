package com.example.firsthibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HibernateInJavaSE {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AlanEM");
    private static final EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    public static void main(String[] args) throws Exception {

//        prepararDatos();

        /* Ejemplo 1 */
//        String query = "SELECT a FROM Address a WHERE a.user.age < 100";
//        List<Address> list = em.createQuery(query, Address.class).getResultList();
//
//        for (Address a : list) {
//            System.out.println(a);
//        }

        /* Ejemplo 2 */
        String query = "SELECT new com.example.firsthibernate.NomCiu(a.user.name, a.ciudad) FROM Address a WHERE a.user.age < 100";
        List<NomCiu> list = em.createQuery(query, NomCiu.class).getResultList();

        for (NomCiu a : list) {
            System.out.println(a);
        }

    }

    private static void prepararDatos() {
        User user = new User("Alan",18);
        Address add1 = new Address("Asuncion", "San Vicente", "Sebastian el cano 1653", user);
        Address add2 = new Address("Villa Elisa", "4 Mojones", "Calle 22 3256", user);
        user.setAddresses(Arrays.asList(add1, add2));


        User user2 = new User("David",35);
        Address u2add1 = new Address("Asuncion", "San Vicente", "Sebastian el cano 1653",user2);
        Address u2add2 = new Address("Labelle", "County", "354 County RD 78", user2);
        user2.setAddresses(Arrays.asList(u2add1, u2add2));

        User user3 = new User("Matusalen",969);
        Address u3add1 = new Address("Miami", "Cool", "Calle 35 6798",user3);
        Address u3add2 = new Address("Labelle", "Nose", "Calle 36 6798", user3);
        Address u3add3 = new Address("Luque", "Lejos", "Calle 37 6798", user3);
        user3.setAddresses(Arrays.asList(u3add1, u3add2, u3add3));

        em.getTransaction().begin();

        em.persist(user);
        em.persist(user2);
        em.persist(user3);

        em.getTransaction().commit();
    }
}
