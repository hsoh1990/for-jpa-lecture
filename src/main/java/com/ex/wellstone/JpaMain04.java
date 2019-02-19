package com.ex.wellstone;

import com.ex.wellstone.entity.Member;
import com.ex.wellstone.entity.MemberType;
import com.ex.wellstone.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain04 {
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

            em.flush();
            em.clear();

            // 다른 로직
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

            System.out.println("============================");
            System.out.println(findTeam.getName());

            List<Member> members = findTeam.getMembers();
            System.out.println("============================");
            members.forEach(member1 -> System.out.println("member1 => " + member1.toString()));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
