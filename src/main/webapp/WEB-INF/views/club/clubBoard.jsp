<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/clubMenu.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/clubBoard.css" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="북클럽 게시판" name="title"/>
</jsp:include>
${club.clubNo}
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="loginMember"/>
</sec:authorize>

<div id="clubBook-container">
	<section id="content">
		<div id="menuDiv">
			<ul>
				<li id="first-li" class="menu-li" style="background-color: #F76E11;"><a href="${pageContext.request.contextPath}/club/clubStory.do/${club.clubNo}">메인페이지</a></li>
				<li id="second-li" class="menu-li" style="background-color: #FF9F45;"><a href="${pageContext.request.contextPath}/club/clubStory.do/${club.clubNo}">북클럽 스토리</a></li>
				<li id="third-li" class="menu-li nowPage" style="background-color: #FFBC80;"><a href="#">게시판</a></li>
				<li id="fourth-li" class="menu-li" style="background-color: #FC4F4F;"><a href="${pageContext.request.contextPath}/club/clubMission.do/${club.clubNo}/${loginMember.username}">미션</a></li>
				<li id="fifth-li" class="menu-li" style="background-color: #D9534F;"><a href="${pageContext.request.contextPath}/club/clubChat.do/${club.clubNo}">채팅..?</a></li>		
			</ul>
		</div>
		<div id="title" class="text-center">
			<h1>📣북클럽 게시판📣</h1>
			<button 
				type="button" 
				class="btn btn-lg btn-link" 
				id="clubBoard-enroll"
				onclick="boardEnroll();">
				<i class="fa-solid fa-plus"></i>
			</button>
		</div>
		<div id="table-div">
			<table>
			  <thead>
			    <tr class="tr">
			      <th scope="col">No</th>
			      <th scope="col">제목</th>
			      <th scope="col">작성자</th>
			      <th scope="col">작성일</th>
			    </tr>
			  </thead>
			  <tbody>
			    <tr>
			      <th scope="row">1</th>
			      <td>이거 책 내용 에바 아닌가여?</td>
			      <td>hosi</td>
			      <td>2022-05-09</td>
			    </tr>
			    <tr>
			      <th scope="row">2</th>
			      <td>정말 너무 감동받았습니다.</td>
			      <td>Thornton</td>
			      <td>2022-05-06</td>
			    </tr>
			    <tr>
			      <th scope="row">3</th>
			      <td>56페이지 맨 아래 문장 너무 좋네요</td>
			      <td>tmddbs</td>
			      <td>2022-09-09</td>
			    </tr>
			  </tbody>
			</table>
		</div>
	</section>

</div>

<form:form 
	action="${pageContext.request.contextPath}/club/clubBoardEnroll.do"
	method="GET"
	name="clubBoardEnrollFrm">
	<input type="hidden" name="memberId" value="${loginMember.username}" />
	<input type="hidden" name="clubNo" value="${clubNo}" />
</form:form>


<script>
	const boardEnroll = () => {
		
		const frm = document.clubBoardEnrollFrm;
		frm.submit();
		
	}

</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>