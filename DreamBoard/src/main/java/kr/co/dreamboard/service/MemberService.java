package kr.co.dreamboard.service;

import kr.co.dreamboard.entity.Member;

public interface MemberService {
	public Member login(Member mvo); // 로그인체크
	public Member findID(Member mvo); // 아이디 찾기
	public int join(Member m); // 회원가입
	public Member checkDuplicate(String memID); // 아이디 중복체크
	public Member getMember(String memID);
	public int modify(Member mvo); // 수정하기
	public void memProfileModify(Member mvo);
}
