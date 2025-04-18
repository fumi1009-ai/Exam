<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報削除</h2>

			<form method="post" action="StudentDeleteExecute.action">
				<div class="border mx-3 mb-3 py-3 px-4 rounded" id="filter">
					<div class="mb-3">
						<label class="form-label" for="ent_year">入学年度</label><br>
						<input class="border border-0 ps-3" type="text" id="ent_year" name="ent_year" value="${student.entYear}" readonly/>
					</div>
					<div class="mb-3">
						<label class="form-label" for="no">学生番号</label><br>
						<input class="border border-0 ps-3" type="text" id="no" value="${student.no}" name="no" readonly/>
					</div>
					<div class="mb-3">
						<label class="form-label" for="name">氏名</label><br>
						<input class="border border-0 ps-3" type="text" id="name" value="${student.name}" name="name" readonly/>
					</div>
					<div class="mb-3">
						<label class="form-label" for="class_num">クラス</label><br>
						<input class="border border-0 ps-3" type="text" id="class_num" value="${student.classNum}" name="class_num" readonly/>
					</div>
					<div class="mx-auto py-2">
						<input type="submit" value="削除">
					</div>
					</div>
				</form>
			<!-- 学生一覧へのリンク -->
				<a href="StudentList.action">戻る</a>

		</section>
	</c:param>
</c:import>

