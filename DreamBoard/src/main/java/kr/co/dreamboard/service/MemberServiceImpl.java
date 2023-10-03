package kr.co.dreamboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dreamboard.entity.Member;
import kr.co.dreamboard.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper memberMapper;

	@Override
	public Member login(Member mvo) {
		return memberMapper.memLogin(mvo);
	}

	@Override
	public Member findID(Member mvo) {
		return memberMapper.findID(mvo);
	}

	@Override
	public int join(Member m) {
		return memberMapper.register(m);
	}

	@Override
	public Member checkDuplicate(String memID) {
		return memberMapper.checkDuplicate(memID);
	}

	@Override
	public int modify(Member mvo) {
		return memberMapper.memUpdate(mvo);
	}

	@Override
	public void memProfileModify(Member mvo) {
		memberMapper.memProfileUpdate(mvo);
	}

	@Override
	public Member getMember(String memID) {
		return memberMapper.getMember(memID);
	}

}
