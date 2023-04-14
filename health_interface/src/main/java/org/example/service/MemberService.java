package org.example.service;

import org.example.pojo.Member;

public interface MemberService {
    public void add(Member member);
    public Member findByTelephone(String telephone);
}
