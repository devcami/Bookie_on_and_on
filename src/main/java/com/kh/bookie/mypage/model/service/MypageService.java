package com.kh.bookie.mypage.model.service;

import java.util.List;
import java.util.Map;

import com.kh.bookie.club.model.dto.Club;
import com.kh.bookie.dokoo.model.dto.Dokoo;
import com.kh.bookie.member.model.dto.Member;
import com.kh.bookie.mypage.model.dto.BookIng;
import com.kh.bookie.mypage.model.dto.Qna;
import com.kh.bookie.pheed.model.dto.Pheed;

public interface MypageService {

	List<Qna> selectMyQnaList(String memberId);

	int qnaEnroll(Qna qna);

	Qna selectOneQna(int qnaNo);

	List<BookIng> SelectMyBookIngList(String memberId);

	List<Club> selectMyClubList(Map<String, Object> map);

	int selectTotalMyClub(Map<String, Object> map);

	List<Pheed> selectMyPheedList(Map<String, Object> map);

	int selectTotalMyDokoo(String memberId);

	List<Dokoo> selectMyDokooList(Map<String, Object> map);

}
