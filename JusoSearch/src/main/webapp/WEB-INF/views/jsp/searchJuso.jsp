<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
<script src="<c:url value="/resource/js/jquery.twbsPagination.js" />"></script>

<div id="myKeywordBox" style="float:left; margin-right:40px;" >
	<c:if test="${fn:length(myHistKeyList) > 0}">
		<h2>나의 키워드 검색 최신 기록</h2>
		<table id="myKeyListTable">
			<tr>
				<th>순위</th>
				<th>키워드</th>
				<th>검색일시</th>
			</tr>
			<c:forEach var="myKeyList" items="${myHistKeyList}" varStatus="stat">
				<tr class="myKeyListTr">
					<td><c:out value="${stat.count}"/></td>
					<td><c:out value="${myKeyList.keyword}"/></td>
					<td><c:out value="${myKeyList.regTime}"/></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>
<div id="popKeywordBox" style="float:left;">
	<c:if test="${fn:length(popKeywordList) > 0}">
		<h2>인기 키워드 검색 TOP 10</h2>
		<table id="popKeyListTable">
			<tr>
				<th>순위</th>
				<th>키워드</th>
				<th>검색조회수</th>
			</tr>
			<c:forEach var="popKeyList" items="${popKeywordList}" varStatus="stat">
				<tr class="popKeyListTr">
					<td><c:out value="${stat.count}"/></td>
					<td><c:out value="${popKeyList.keyword}"/></td>
					<td><c:out value="${popKeyList.cnt}"/></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>
<br><br>

<div style="clear:both;">
	<form id="keywordForm">
		키워드 : <input type="text" id="keyword" name="keyword" value="" onkeypress="if(event.keyCode==13) {searchKeyword('1'); return false;}"><br>
		<input type="hidden" id="currentPage" name="currentPage" value="1">
		<button type="button" onclick="searchKeyword('1');">조회</button>
	</form>
	<button type="button" onclick="logout();">로그아웃</button>


	<input type="hidden" id="totalPage" value="">
	<br><br>
	<div id="listBox" style="display:none;"></div>
	
	<div id="pagingBox" class="container" style="display:none;">
	    <nav aria-label="Page navigation">
	        <ul class="pagination" id="pagination"></ul>
	    </nav>
	</div>
	
	<div id="detailBox"></div>
</div>