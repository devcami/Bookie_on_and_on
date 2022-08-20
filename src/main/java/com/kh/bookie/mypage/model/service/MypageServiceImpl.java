package com.kh.bookie.mypage.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.bookie.member.model.dto.Follower;
import com.kh.bookie.member.model.dto.Member;
import com.kh.bookie.mypage.model.dao.MypageDao;
import com.kh.bookie.mypage.model.dto.BookIng;
import com.kh.bookie.mypage.model.dto.Qna;

@Service
public class MypageServiceImpl implements MypageService {

	@Autowired
	MypageDao mypageDao;
	
	@Override
	public List<Qna> selectMyQnaList(String memberId) {
		return mypageDao.selectMyQnaList(memberId);
	}
	
	@Override
	public int getFollowers(String memberId) {
		return mypageDao.getFollowers(memberId);
	}
	
	@Override
	public int getFollowing(String memberId) {
		return mypageDao.getFollowing(memberId);
	}
	
	@Override
	public List<Follower> selectFollowerList(String memberId) {
		return mypageDao.selectFollowerList(memberId);
	}
	
	@Override
	public List<Follower> selectFollowingList(String memberId) {
		return mypageDao.selectFollowingList(memberId);
	}
	
	@Override
	public int qnaEnroll(Qna qna) {
		return mypageDao.qnaEnroll(qna);
	}
	
	@Override
	public Qna selectOneQna(int qnaNo) {
		return mypageDao.selectOneQna(qnaNo);
	}

	@Override
	public List<BookIng> SelectMyBookIngList(String memberId) {
		return mypageDao.SelectMyBookIngList(memberId);
	}
}
