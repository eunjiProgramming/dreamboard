package kr.co.dreamboard.mapper;

import java.util.List;

import kr.co.dreamboard.entity.Board;
import kr.co.dreamboard.entity.Criteria;
import kr.co.dreamboard.entity.Member;

// @Mapper -> 생략 가능
public interface BoardMapper { // @, XML
	public List<Board> getList(Criteria cri);
	public void insert(Board VO);
	public void insertSelectKey(Board vo);
	public Member login(Member vo); // SQL
	public Board read(int idx);
	public void update(Board vo);
	public void delete(int idx);
	public void replySeqUpdate(Board parent);
	public void replyInsert(Board vo);
	public int totalCount(Criteria cri);
	public void countUpdate(int idx);
}
