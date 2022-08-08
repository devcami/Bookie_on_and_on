package com.kh.bookie.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.bookie.admin.model.dto.Alarm;
import com.kh.bookie.admin.model.dto.Report;
import com.kh.bookie.admin.model.service.AdminService;
import com.kh.bookie.dokoo.model.dto.Dokoo;
import com.kh.bookie.dokoo.model.dto.DokooComment;
import com.kh.bookie.dokoo.model.service.DokooService;
import com.kh.bookie.member.model.dto.Member;
import com.kh.bookie.member.model.service.MemberService;
import com.kh.bookie.pheed.model.dto.Pheed;
import com.kh.bookie.pheed.model.dto.PheedComment;
import com.kh.bookie.pheed.model.service.PheedService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
@SessionAttributes({"next"})
public class AdminController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	AdminService adminService;

	@Autowired
	DokooService dokooService;
	
	@Autowired
	PheedService pheedService;
	
	@GetMapping("/memberList.do")
	public void admin(Model model) {
		try {
			List<Member> list = memberService.selectMemberList();
			log.debug("list = {}",list);
			model.addAttribute("list", list);
		}catch(Exception e){
			log.error("회원괸리 오류",e);
			throw e;
		}
		
	}
	
	@GetMapping("/sendAlarm.do")
	public void sendAlarm(Model model) {
		try {
			List<Member> list = memberService.selectMemberList();
			log.debug("list = {}",list);
			model.addAttribute("list", list);
		}catch(Exception e){
			log.error("회원괸리 오류",e);
			throw e;
		}
	};
	
	@GetMapping("/selectMemberListByInterest.do")
	public ResponseEntity<?> selectMemberListByInterest(@RequestParam String interest){
		Map<String, Object> map = new HashMap<>();
		try {
			Member member = new Member();
			member.setInterest(interest);
			List<Member> list = memberService.selectMemberListByInterest(member);
			map.put("msg", "관심사별 멤버 불러오기 완료");
			map.put("list", list);
		} catch (Exception e) {
			log.error("관심사별 멤버 불러오기 오류", e);
			map.put("msg", "관심사별 멤버 불러오기 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		return ResponseEntity.ok(map);
	}
	
	@PostMapping("/insertAlarm.do")
	public ResponseEntity<?> insertAlarm(Alarm alarm){
		Map<String, Object> map = new HashMap<>();
		try {
			int result = adminService.insertAlarm(alarm);
			map.put("msg", "알림 등록 완료");
		} catch (Exception e) {
			log.error("알림 등록 오류", e);
			map.put("msg", "알림 등록 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/reportList.do")
	public void reportList(Model model) {
		try {
			List<Report> list = adminService.selectReportList();
			log.debug("list = {}",list);
			model.addAttribute("list", list);
		}catch(Exception e){
			log.error("신고리스트 불러오기 오류",e);
			throw e;
		}
	}
	
	@GetMapping("/selectReportListByCategory.do")
	public ResponseEntity<?> selectReportListByCategory(@RequestParam String category , @RequestParam String status){
		Map<String, Object> map = new HashMap<>();
		try {
			List<Report> list = null;
			if(!status.equals("상태") && category.equals("카테고리")) {
				// 상태는 선택되고 카테고리는 전체일 시
				list = adminService.selectReportListByStatus(status);
			} 
			else if(status.equals("상태") && category.equals("카테고리")) {
				// 둘다 없을 시
				list = adminService.selectReportList();
			}
			else if(status.equals("상태")) {
				// 카테고리만 선택 시
				list = adminService.selectReportListByCategory(category);
			}
			else if(!status.equals("상태")){
				// 둘다 선택 시
				map.put("category", category);
				map.put("status", status);
				list = adminService.selectReportListByBoth(map);
			} 
			map.put("list", list);
		} catch (Exception e) {
			log.error("신고리스트 -카테고리 불러오기 오류", e);
			map.put("msg", "신고리스트 -카테고리 불러오기 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/selectReportListByStatus.do")
	public ResponseEntity<?> selectReportListByStatus(@RequestParam String status, @RequestParam String category){
		Map<String, Object> map = new HashMap<>();
		try {
			List<Report> list = null;
			if(!category.equals("카테고리") && status.equals("상태")){
				// 카테고리는 선택되고 상태는 전체일 시
				list = adminService.selectReportListByCategory(category);
			} 
			else if(status.equals("상태") && category.equals("카테고리")) {
				// 둘다 없을 시
				list = adminService.selectReportList();
			}
			else if(category.equals("카테고리")) {
				// 상태만 선택 시
				list = adminService.selectReportListByStatus(status);
			} 
			else if(!category.equals("카테고리")){
				// 둘다 선택 시
				map.put("category", category);
				map.put("status", status);
				list = adminService.selectReportListByBoth(map);
			} 
			map.put("list", list);
		} catch (Exception e) {
			log.error("신고리스트 -상태 불러오기 오류", e);
			map.put("msg", "신고리스트 -상태 불러오기 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/reportDetail.do")
	public void reportDetail(@RequestParam int reportNo, Model model) {
		try {
			Report report = adminService.selectOneReport(reportNo);
			model.addAttribute("report", report);
			log.debug("report = {}", report);
			
			if(report.getCategory().equals("dokoo") || report.getCategory().equals("dokoo_comment")) {
				// dokoo - select one dokoo 가져오기
				if(report.getCategory().equals("dokoo")) {
					Dokoo dokoo = dokooService.selectOneDokoo(report.getBeenziNo());
					model.addAttribute("dokoo", dokoo);
				}
				// dokooComment - select one dokoo 가져오기
				else if(report.getCategory().equals("dokoo_comment")){
					int dokooCNo = report.getBeenziNo();
					DokooComment dokooComment = dokooService.selectOneDokooComment(dokooCNo);
					if(dokooComment != null) {
					log.debug("dokooComment = {}", dokooComment);
					int dokooNo = dokooComment.getDokooNo();
					Dokoo dokoo = dokooService.selectOneDokoo(dokooNo);
					log.debug("dokoo = {}", dokoo);
					model.addAttribute("dokoo", dokoo);
					}
				}
				
			} else if(report.getCategory().equals("pheed") || report.getCategory().equals("pheed_comment")){
				// pheed - select one pheed 가져오기
				if(report.getCategory().equals("pheed")) {
					Pheed pheed = pheedService.selectOnePheed(report.getBeenziNo());
					model.addAttribute("pheed", pheed);
					List<PheedComment> commentList = pheedService.selectPheedCommentList(report.getBeenziNo());
					model.addAttribute("commentList", commentList);
				}
				// pheedComment - select one pheed 가져오기
				else if(report.getCategory().equals("pheed_comment")) {
					int pheedCNo = report.getBeenziNo(); // 57
					PheedComment pheedComment = pheedService.selectOnePheedComment(pheedCNo);
					log.debug("pheedComment = {}", pheedComment);
					if(pheedComment != null) {
						int pheedNo = pheedComment.getPheedNo();
						Pheed pheed = pheedService.selectOnePheed(pheedNo);
						log.debug("pheed", pheed);
						model.addAttribute("pheed", pheed);
						List<PheedComment> commentList = pheedService.selectPheedCommentList(pheedNo);
						model.addAttribute("commentList", commentList);
					}
				}
			}
		}catch(Exception e){
			log.error("신고 상세보기 오류",e);
			throw e;
		}
	}
	
	@PostMapping("/reportUpdate.do")
	public String reportUpdate(@RequestParam int reportNo, @RequestParam String status) {
		Map<String, Object> map = new HashMap<>();
		try {
			log.debug("status", status);
			map.put("status", status);
			map.put("reportNo", reportNo);
			int result = adminService.reportUpdate(map);
		} catch (Exception e) {
			log.error("신고 상태 수정 오류", e);
		}
		return "redirect:/admin/reportDetail.do?reportNo=" + reportNo;
	}
	
	@PostMapping("/reportDelete.do")
	public ResponseEntity<?> reportDelete(@RequestParam String category, @RequestParam int beenziNo){
		Map<String, Object> map = new HashMap<>();
		int result = 0;
		try {
			if(category.equals("dokoo")) {
				result = dokooService.deleteDokoo(beenziNo);
			} else if(category.equals("dokoo_comment")) {
				result = dokooService.commentDel(beenziNo);
			} else if(category.equals("pheed")) {
				result = pheedService.deletePheed(beenziNo);
			} else if(category.equals("pheed_comment")) {
				result = pheedService.commentDel(beenziNo);
			}
		} catch (Exception e) {
			log.error("신고 글 삭제 오류", e);
			map.put("msg", "신고 글 삭제 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		return ResponseEntity.ok(map);
		
	}
}
