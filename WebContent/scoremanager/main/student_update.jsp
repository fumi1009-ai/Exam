<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報更新</h2>

			<form method="post" action="StudentUpdateExecute.action">
<div class="border mx-3 mb-3 py-3 px-4 rounded" id="filter">
<div class="mb-3">
<label class="form-label" for="ent_year">入学年度</label><br>
<input class="border border-0 ps-3" type="text" id="ent_year" name="ent_year" value="${ent_year }" readonly/>
</div>
<div class="mb-3">
<label class="form-label" for="no">学生番号</label><br>
<input class="border border-0 ps-3" type="text" id="no" value="${no }" name="no" readonly/>
</div>
<div class="mb-3">
<label class="form-label" for="name">氏名</label>
<input type="text" name="name" class="form-control" id="name" value="${name}" placeholder="氏名を入力下さい"required/>
</div>
<div class="mb-3">
<label class="form-label" for="student-f2-select">クラス</label>
<select class="form-select" id="student-f2-select" name="class_num">
<c:forEach var="class_num" items="${class_num_set}">
<option value="${class_num}" <c:if test="${class_num == param.class_num}">selected</c:if>>${class_num}</option>
</c:forEach>
</select>
</div>
<div class="col-2 form-check text-center">
<label for="is_attend">在学中</label>
<input type="checkbox" id="is_attend" name="is_attend"<c:if test="${is_attend }">checked</c:if> />
</div>

				<input type="submit" value="変更">
</div>
</form>
<!-- 学生一覧へのリンク -->
<a href="StudentList.action">戻る</a>

		</section>
</c:param>
</c:import>