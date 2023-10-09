package kr.co.dreamboard.service;

import java.util.List;

import kr.co.dreamboard.entity.Board;
import kr.co.dreamboard.entity.Criteria;
import kr.co.dreamboard.entity.Member;

public interface BoardService {

	public List<Board> getList(Criteria cri);
	public Member login(Member vo);
	public void register(Board vo);
	public Board get(int boardIdx);
	public void modify(Board vo);
	public void remove(int boardIdx);
	public void replyProcess(Board vo);
	public int totalCount(Criteria cri);
}
