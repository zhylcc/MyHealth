package org.example.dao;

import org.example.pojo.Member;

public interface MemberDao {
    public void add(Member member);
    public Member findByTelephone(String telephone);

    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();
}
