<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
	<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>

				<form method="post" action="SubjectCreateExecute.action">
	<div class="border mx-3 mb-3 py-3 px-4 rounded" id="filter">

                <!-- 科目コード -->
                <div class="mb-3">
                    <label class="form-label" for="cd">科目コード</label>
                    <input type="text" name="cd" class="form-control" id="cd" value="${cd}" placeholder="教科コードを入力してください" required />
                </div>
                <div class="mt-2 text-warning">${errors.get("cd")}</div>

                <!-- 科目名 -->
                <div class="mb-3">
                    <label class="form-label" for="name">科目名</label>
                    <input type="text" name="name" class="form-control" id="name" value="${name}" placeholder="教科名を入力してください" required />
                </div>
                <div class="mt-2 text-warning">${errors.get("name")}</div>

	<!-- 登録して終了ボタン -->

	<input type="submit" value="登録">
	</div>
	</form>
	<!-- 学生一覧へのリンク -->
	<a href="subject_list.jsp">戻る</a>
    </c:param>
</c:import>
