package kr.co.dreamboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dreamboard.entity.Member;

@Mapper //- Mybatis API
public interface MemberMapper {	 
	public Member memLogin(Member mvo); // 로그인체크
	public Member findID(Member mvo); // 아이디 찾기
	public int register(Member m); //회원등록( 성공1, 실패0 )
	public Member checkDuplicate(String memID);
	public Member getMember(String memID);
	public int memUpdate(Member mvo); // 수정하기
	public void memProfileUpdate(Member mvo);
}