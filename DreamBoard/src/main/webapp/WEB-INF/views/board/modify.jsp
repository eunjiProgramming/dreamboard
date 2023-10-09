<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
 <title>Dream Board</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8fdefe0992d4504ccd8895d4c299d090"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css"> 
  <script src="${contextPath}/resources/javascript/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("button").on("click", function(e) {
			var formData = $("#frm");
			var btn = $(this).data("btn"); // data-btn="list"
			if (btn == 'modify') {
				formData.attr("action", "${contextPath}/board/modify");
			} else if (btn == 'remove') {
				formData.find("#title").remove();
				formData.find("#content").remove();
				formData.find("#writer").remove();
				formData.attr("action", "${contextPath}/board/remove");
				formData.attr("method", "get")
			} else if (btn == 'list') {
				formData.find("#boardIdx").remove();
				formData.find("#title").remove();
				formData.find("#content").remove();
				formData.find("#writer").remove();
				formData.attr("action", "${contextPath}/board/list");
				formData.attr("method", "get")
			}
			formData.submit();
		});
	});
</script>
</head>
<body>
 <jsp:include page="../common/header.jsp"/> 
	<div class="card">
		<div class="card-body">
			<div class="row">
				<div class="col-lg-2">
					<jsp:include page="left.jsp" />
				</div>
				<div class="col-lg-7">
					<form id="frm" method="post">
						<table class="table table-bordered">
							<tr>
								<td>번호</td>
								<td><input type="text" class="form-control" name="boardIdx" readonly="readonly" value="${vo.boardIdx}" /></td>
							</tr>
							<tr>
								<td>제목</td>
								<td><input type="text" class="form-control" name="title" value="<c:out value='${vo.title}'/>" /></td>
							</tr>
							<tr>
								<td>내용</td>
								<td><textarea rows="10" class="form-control" name="content"><c:out value='${vo.content}' /></textarea></td>
							</tr>
							<tr>
								<td>작성자</td>
								<td><input type="text" class="form-control" name="writer" readonly="readonly" value="${vo.writer}" /></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center;">
								<c:if test="${!empty mvo && mvo.memID eq vo.memID}">
										<button type="button" data-btn="modify" class="btn btn-sm btn-primary">수정</button>
										<button type="button" data-btn="remove" class="btn btn-sm btn-warning">삭제</button>
								</c:if> 
								<c:if test="${empty mvo || mvo.memID ne vo.memID}">
									<button disabled="disabled" type="button" class="btn btn-sm btn-primary">수정</button>
									<button disabled="disabled" type="button" class="btn btn-sm btn-warning">삭제</button>
								</c:if>
									<button type="button" data-btn="list" class="btn btn-sm btn-info">목록</button></td>
							</tr>
						</table>
						<input type="hidden" name="page" value="<c:out value='${cri.page}'/>" />
						<input type="hidden" name="perPageNum" value="<c:out value='${cri.perPageNum}'/>" />
						<input type="hidden" name="type" value="<c:out value='${cri.type}'/>" />
						<input type="hidden" name="keyword" value="<c:out value='${cri.keyword}'/>" />
					</form>
				</div>
				<div class="col-lg-3">
					<jsp:include page="right.jsp" />
				</div>
			</div>
		</div>
		<div class="card-footer">DreamBoard_권은지</div>
	</div>

</body>
</html>