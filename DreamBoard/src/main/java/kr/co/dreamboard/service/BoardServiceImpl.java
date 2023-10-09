package kr.co.dreamboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dreamboard.entity.Board;
import kr.co.dreamboard.entity.Criteria;
import kr.co.dreamboard.entity.Member;
import kr.co.dreamboard.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;
	
	@Override
	public List<Board> getList(Criteria cri) {
		// 반영할 로직~~~
		List<Board> list=boardMapper.getList(cri);
		
		return list;
	}
	
	@Override
	public Member login(Member vo) {
		Member mvo=boardMapper.login(vo);
		return mvo;
	}

	@Override
	public void register(Board vo) {
		boardMapper.insertSelectKey(vo);
	}

	@Override
	public Board get(int boardIdx) {
		Board vo=boardMapper.read(boardIdx);
		boardMapper.countUpdate(boardIdx);
		return vo;
	}

	@Override
	public void modify(Board vo) {
		boardMapper.update(vo);		
	}
	
	@Override
	public void remove(int boardIdx) {
		boardMapper.delete(boardIdx);		
	}
	
	@Override
	public void replyProcess(Board vo) {
		// - 답글만들기
		// 1. 부모글(원글)의 정보를 가져오기(vo->boardIdx)
		Board parent=boardMapper.read(vo.getBoardIdx());
		// 2. 부모글의 boardGroup의 값을->답글(vo)정보에 저장하기
		vo.setBoardGroup(parent.getBoardGroup());
		// 3. 부모글의 boardSequence의 값을 1을 더해서 ->답글(vo)정보에 저장하기
		vo.setBoardSequence(parent.getBoardSequence()+1);
		// 4. 부모글의 boardLevel의 값을 1을 더해서 ->답글(vo)정보에 저장하기
        vo.setBoardLevel(parent.getBoardLevel()+1);
        // 5. 같은 boardGroup에 있는 글 중에서
        //    부모글의 boardSequence보다 큰 값들을 모두 1씩 업데이트하기
		boardMapper.replySeqUpdate(parent);
		// 6. 답글(vo)을 저정하기
		boardMapper.replyInsert(vo);
	}
	
	@Override
	public int totalCount(Criteria cri) {		
		return boardMapper.totalCount(cri);
	}	
}
