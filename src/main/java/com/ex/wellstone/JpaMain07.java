package com.ex.wellstone;

import com.ex.wellstone.entity.Member;
import com.ex.wellstone.entity.MemberType;
import com.ex.wellstone.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain07 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("hello");
            member.setMemberType(MemberType.USER);
            member.setTeam(team);
            em.persist(member);

            String jpql = " select m From Member m where m.name like '%hello%'";
            List<Member> result = em.createQuery(jpql, Member.class).getResultList();
            result.forEach(member1 -> System.out.println("member like => " + member1.toString()));

            jpql = " select m From Member m join fetch m.team where m.name like '%hello%'";
            result = em.createQuery(jpql, Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();
            result.forEach(member1 -> System.out.println("member join fetch => " + member1.toString()));

            //spring @query 사용시 동작
            result = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "hello")
                    .getResultList();
            result.forEach(member1 -> System.out.println("member NamedQuery => " + member1.toString()));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
