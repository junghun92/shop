package com.shop.entity;

import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "gildong", roles = "USER")
    public void auditingTest(){
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(member.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("member1.getRegTime() = " + member1.getRegTime());
        System.out.println("member1.getUpdateTime() = " + member1.getUpdateTime());
        System.out.println("member1.getCreateBy() = " + member1.getCreateBy());
        System.out.println("member1.getModifiedBy() = " + member1.getModifiedBy());
    }
}