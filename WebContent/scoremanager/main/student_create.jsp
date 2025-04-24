<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

			<form method="post" action="StudentCreateExecute.action">
<div class="border mx-3 mb-3 py-3 px-4 rounded" id="filter">

					<!-- 入学年度 -->
<div class="mb-3">
<label class="form-label" for="student-f1-select">入学年度</label>
<select class="form-select" id="student-f1-select" name="ent_year">
<option value="0">--------</option>
<c:forEach var="year" items="${ent_year_set}">
<option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>${year}</option>
</c:forEach>
</select>
<div class="mt-2 text-warning">${errors.get("1") }</div>
</div>

					<!-- 学生番号 -->
<div class="mb-3">
<label class="form-label" for="no">学生番号</label>
<input type="text" name="no" class="form-control" id="no" value="${no}" placeholder="学生番号を入力下さい"required />
</div>
<div class="mt-2 text-warning">${errors.get("2") }</div>

					<!-- 氏名 -->
<div class="mb-3">
<label class="form-label" for="name">氏名</label>
<input type="text" name="name" class="form-control" id="name" value="${name}" placeholder="氏名を入力下さい"required/>
</div>

					<!-- クラス -->
<div class="mb-3">
<label class="form-label" for="student-f2-select">クラス</label>
<select class="form-select" id="student-f2-select" name="class_num">
<c:forEach var="class_num" items="${class_num_set}" varStatus="status">
<option value="${class_num}"
<c:if test="${empty param.class_num && status.first}">selected</c:if>
<c:if test="${class_num == param.class_num}">selected</c:if>>

                ${class_num}
</option>
</c:forEach>
</select>
</div>

<!-- 登録して終了ボタン -->

<input type="submit" value="登録">
</div>
</form>
<!-- 学生一覧へのリンク -->
<a href="student_list.jsp">戻る</a>

		</section>
</c:param>
</c:import>
