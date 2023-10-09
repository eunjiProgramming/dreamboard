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
			if (btn == 'reply') {
				formData.attr("action", "${contextPath}/board/reply");
			} else if (btn == 'list') {
				var formData1 = $("#frm1");
				formData1.attr("action", "${contextPath}/board/list");
				formData1.submit();
				return;
			} else if (btn == 'reset') {
				formData[0].reset();
				return;
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
						<input type="hidden" name="page" value="<c:out value='${cri.page}'/>" />
						<input type="hidden" name="perPageNum" value="<c:out value='${cri.perPageNum}'/>" /> 
						<input type="hidden" name="type" value="<c:out value='${cri.type}'/>" />
						<input type="hidden" name="keyword" value="<c:out value='${cri.keyword}'/>" />
							
						<!-- boardIdx(원글, 부모글) -->
						<input type="hidden" name="boardIdx" value="${vo.boardIdx}"> <input
							type="hidden" name="memID" value="${mvo.memID}" />
						<div class="form-group">
							<label>제목</label>
							<input type="text" name="title" class="form-control" value="<c:out value='${vo.title}'/>">
						</div>
						<div class="form-group">
							<label>답변</label>
							<textarea rows="10" name="content" class="form-control"></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input type="text" readonly="readonly" name="writer" class="form-control" value="${mvo.memName}" />
						</div>
						<button type="button" data-btn="reply" class="btn btn-secondary btn-sm">답변</button>
						<button type="button" data-btn="reset" class="btn btn-secondary btn-sm">취소</button>
						<button type="button" data-btn="list" class="btn btn-secondary btn-sm">목록</button>
					</form>
					<form id="frm1" method="get"> 
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