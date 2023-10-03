package kr.co.dreamboard.service;

import java.util.List;

import kr.co.dreamboard.entity.Board;
import kr.co.dreamboard.entity.Criteria;
import kr.co.dreamboard.entity.Member;

public interface BoardService {

	public List<Board> getList(Criteria cri);
	public Member login(Member vo);
	public void register(Board vo);
	public Board get(int idx);
	public void modify(Board vo);
	public void remove(int idx);
	public void replyProcess(Board vo);
	public int totalCount(Criteria cri);
}
