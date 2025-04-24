<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

    <form method="post" action="SubjectDeleteExecute.action">
<div class="border mx-3 mb-3 py-3 px-4 rounded" id="filter">
<div class="mb-3">
<label class="form-label" for="name">「${subject.name}」を削除してもよろしいですか？</label>
</div>

    		<!-- 削除対象の科目コードを渡す hidden input -->
<input type="hidden" name="cd" value="${subject.cd}">
<input class="btn btn-danger" type="submit" value="削除">
</div>
</form>

      <br><br><br><br>
<!-- 科目一覧へのリンク -->
<a href="SubjectList.action">戻る</a>
</section>
</c:param>
</c:import>
