package org.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.MemberDao;
import org.example.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }
}
