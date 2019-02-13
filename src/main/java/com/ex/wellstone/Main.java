package com.ex.wellstone;

import com.ex.wellstone.entity.Member;
import com.ex.wellstone.entity.MemberType;
import com.ex.wellstone.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
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
            member.setName("hsoh");
            member.setMemberType(MemberType.ADMIN);
            member.setTeam(team);
            em.persist(member);

            // 다른 로직
            Member findMember = em.find(Member.class, member.getId());

            System.out.println("============================");
            System.out.println(findMember.getTeam().getName());
            System.out.println("============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
