<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="content">
		<div id="wrap-box">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報削除</h2>
			<div id="wrap-box">
				<p class="text-center" style="backround-color:#8cc3a9">削除が完了しました</p>

            	<br><br><br>

				<a href="StudentList.action">学生一覧</a>
			</div>
		</div>
	</c:param>
</c:import>