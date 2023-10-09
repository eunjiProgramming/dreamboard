package kr.co.dreamboard.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.dreamboard.entity.Member;

@Mapper //- Mybatis API
public interface MemberMapper {	 
	public Member memLogin(Member mvo); // 로그인체크
	public Member findID(Member mvo); // 아이디 찾기
	public Member findPwd(Member mvo); // 비밀번호 찾기
	public int updatePasswordByMemID(Map<String, String> params); // 비밀번호 변경
	public int register(Member m); //회원등록( 성공1, 실패0 )
	public Member checkDuplicate(String memID);
	public Member getMember(String memID);
	public int memUpdate(Member mvo); // 수정하기
	public void memProfileUpdate(Member mvo);
	public void deactivateMember(String memID); // 회원 탈퇴
}