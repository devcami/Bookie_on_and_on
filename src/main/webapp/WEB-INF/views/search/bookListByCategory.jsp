<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/category.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/search.css" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="분야별 도서리스트" name="title"/>
</jsp:include>
<section id="category-content">
	<div id="category-header" class="text-center">
		<h1>분야별 최신 도서</h1> 
		<p id="resultP" ></p>
		<div class="btns text-left" >
			<button type="button" class="btn bg-white btn-lg" onclick="getPage(1, 'itemNewAll');">최신순</button>
			<button type="button" class="btn bg-white btn-lg" onclick="getPage(1, 'Bestseller');">베스트순</button>
		</div>
	</div>
	<div id="book-container">
	</div>
	<div id='btn-more-container'>
		<button id="btn-more" class="btn gap-2 col-12" type="button">더보기</button>
		<span style="display:none;" id="cPage">1</span>
	</div>
</section>

<script>
window.addEventListener('load', () => {
	const p = document.querySelector("#resultP");
	const category = '${category}';
	console.log(category);
	if(category == 'economy') p.innerText = '경제 ➡︎ 경제/경영';
	if(category == 'computer') p.innerText = '공학 ➡︎ 컴퓨터/모바일';
	if(category == 'science') p.innerText = '공학 ➡︎ 과학';
	if(category == 'novel') p.innerText = '문학 ➡︎ 소설/시/희곡';
	if(category == 'classic') p.innerText = '문학 ➡︎ 고전';
	if(category == 'fiction') p.innerText = '문학 ➡︎ 장르소설';
	if(category == 'selfImprovement') p.innerText = '자기계발 ➡︎ 자기계발';
	if(category == 'language') p.innerText = '언어 ➡︎ 외국어';
	if(category == 'travel') p.innerText = '취미 ➡︎ 여행';
	if(category == 'home') p.innerText = '취미 ➡︎ 가정/요리/뷰티';
	if(category == 'health') p.innerText = '취미 ➡︎ 건강/취미/레저';
	if(category == 'essay') p.innerText = '에세이 ➡︎ 에세이'
	if(category == 'art') p.innerText = '예술 ➡︎ 예술/대중문화';
	if(category == 'children') p.innerText = '교육 ➡︎ 유아/어린이/청소년';
	if(category == 'parent') p.innerText = '교육 ➡︎ 좋은부모';
	if(category == 'certification') p.innerText = '교육 ➡︎ 수험서/자격증';
	if(category == 'professional') p.innerText = '교육 ➡︎ 대학교재/전문서적';
	if(category == 'reference') p.innerText = '교육 ➡︎ 참고서';
	if(category == 'history') p.innerText = '인문학 ➡︎ 역사';
	if(category == 'humanities') p.innerText = '인문학 ➡︎ 인문학';
	if(category == 'socialScience') p.innerText = '인문학 ➡︎ 사회과학';
	if(category == 'religion') p.innerText = '종교 ➡︎ 종교/역학';
	if(category == 'cartoon') p.innerText = '기타 ➡︎ 만화';
	if(category == 'magazine') p.innerText = '기타 ➡︎ 잡지';
	if(category == 'etc') p.innerText = '기타 ➡︎ 사전/기타';
	getPage(1, 'ItemNewAll');
});
const getCategory = () => {
	
}
const getPage = (cPage, sort) => {
	//console.log(cPage, maxResult);
	const container = document.querySelector("#book-container");
	container.innerHTML = "";
	let book = {
			ttbkey : 'ttbiaj96820130001',
			QueryType : sort,
			SearchTarget: 'Book',
			Start : cPage,
			MaxResults : 20,
			Output : 'js',
			Cover : 'mini',
			Version : '20131101',
			CategoryId : '3103',
	};
	$.ajax({
		url : `${pageContext.request.contextPath}/search/selectBookListByCategory.do`,
		data : book,
		contentType : "application/json; charset=utf-8",
		success(resp){
			const {item} = resp;
			//console.log(item);
			item.forEach((book) => {
				const {isbn13, title, author, publisher, pubDate, cover} = book;
				const div = `
					<div class="book-table" onclick="bookEnroll(this);">
						<input type="hidden" name="isbn13" value=\${isbn13} />
						<table class="tbl">
							<tr>
								<td rowspan="4">
									<img src=\${cover} style="width:65px;" />
								</td>
								<td colspan="5" class="book-title">\${title}</td>
							</tr>
							<tr>
								<td class="book-author">\${author}</td>
							</tr>
							<tr>
								<td colspan="2" class="book-p">출판사 : \${publisher} 🧡 출판일 : \${pubDate}</td>
							</tr>
						</table>
					</div>`;
				container.insertAdjacentHTML('beforeend', div);
			});
		},
		error : console.log,
		complete(){
			if(cPage == 10){
				const btn = document.querySelector("#btn-more")
				btn.disabled = "disabled";
				btn.style.cursor = "not-allowed";
			}
		}
	});
};
<%-- 더보기 --%>
document.querySelector("#btn-more").onclick = () => {
	let c = Number(document.querySelector("#cPage").innerText);
	document.querySelector("#cPage").innerText = c + 1;
	getPage(c + 1, );
};
<%-- 베스트 버튼 클릭 시 --%>

<%-- 책 클릭 시 내 서재에 등록 폼 --%>
const bookEnroll = (e) => {
	console.log(e.firstElementChild.value);	
	const isbn13 = e.firstElementChild.value;
	location.href = "${pageContext.request.contextPath}/search/bookEnroll.do?isbn13=" + isbn13;
};

</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>