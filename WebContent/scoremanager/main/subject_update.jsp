<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報更新</h2>

<form method="post" action="SubjectUpdateExecute.action">
<div class="border mx-3 mb-3 py-3 px-4 rounded" id="filter">
</div>

	<div class="mb-3">
<label class="form-label" for="no">科目コード</label><br>
<input class="border border-0 ps-3" type="text" id="cd" value="${cd}" name="cd" readonly/>
</div>
<div class="mb-3">
<label class="form-label" for="name">科目名</label>
<input type="text" name="name" class="form-control" id="name" value="${name}" placeholder="科目名を入力して下さい" required/>
</div>

	<input type="submit" value="変更">

</form>
<br>
<!-- 科目一覧へのリンク -->
<a href="SubjectList.action">戻る</a>

</section>
</c:param>
</c:import>
