<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
<c:param name="title">

        得点管理システム - 成績参照
</c:param>
<c:param name="content">
<div class="container-fluid px-4">
<!-- Search Form Card -->
<div class="card mb-4">
<div class="card-header bg-light py-2">
<h2 class="h3 mb-0">成績参照</h2>
</div>
<div class="card-body p-0">
<!-- 科目情報 Section -->
<div class="p-4 pb-2">
<form method="get" action="TestListSubjectExecute.action" class="mb-0">
<input type="hidden" name="f" value="sj">
<div class="row align-items-end">
<div class="col-md-2 mb-2">
<h4 class="h6 mb-0 fw-bold">科目情報</h4>
</div>
<div class="col-md-2 mb-2">
<label class="form-label small mb-1">入学年度</label>
<select class="form-select form-select-sm" name="f1">
<option value="">--------</option>
<c:forEach var="year" items="${ent_year_set}">
<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
</c:forEach>
</select>
</div>
<div class="col-md-2 mb-2">
<label class="form-label small mb-1">クラス</label>
<select class="form-select form-select-sm" name="f2">
<option value="">--------</option>
<c:forEach var="num" items="${class_num_set}">
<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
</c:forEach>
</select>
</div>
<div class="col-md-3 mb-2">
<label class="form-label small mb-1">科目</label>
<select class="form-select form-select-sm" name="f3">
<option value="">--------</option>
<c:forEach var="subject" items="${subject_set}">
<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
</c:forEach>
</select>
</div>
<div class="col-md-2 mb-2 d-flex align-items-end">
<button type="submit" class="btn btn-secondary btn-sm w-100">確定</button>
</div>
</div>
</form>
</div>

                    <!-- Divider Line -->
<hr class="mx-4 my-1">

                    <!-- 学生情報 Section -->
<div class="p-4 pt-2">
<form action="TestListStudentExecute.action" method="get" class="mb-0">
<input type="hidden" name="f" value="st">
<div class="row align-items-end">
<div class="col-md-2 mb-2">
<h4 class="h6 mb-0 fw-bold">学生情報</h4>
</div>
<div class="col-md-4 mb-2">
<label class="form-label small mb-1">学生番号</label>
<input type="text" class="form-control form-control-sm" name="f4"

                                        placeholder="学生番号を入力してください"

                                        value="${f4}"

                                        maxlength="10">
</div>
<div class="col-md-2 mb-2 d-flex align-items-end">
<button type="submit" class="btn btn-secondary btn-sm w-100">検索</button>
</div>
</div>
</form>
</div>
</div>
</div>

            <!-- Error Messages -->
<c:if test="${not empty errors}">
<div class="alert alert-danger">
<c:forEach var="error" items="${errors}">
<div>${error.value}</div>
</c:forEach>
</div>
</c:if>

            <!-- Subject Test Results -->
<c:if test="${not empty subject}">
<div class="card mb-4">
<div class="card-header bg-light py-2">
<h3 class="h5 mb-0">成績一覧（科目）: ${subject.name}</h3>
</div>
<div class="card-body">
<table class="table table-bordered table-sm">
<thead class="bg-light">
<tr>
<th>入学年度</th>
<th>クラス</th>
<th>学生番号</th>
<th>氏名</th>
<c:forEach begin="1" end="2" var="testNo">
<th class="text-center">${testNo}回</th>
</c:forEach>
</tr>
</thead>
<tbody>
<c:forEach var="result" items="${testResults}">
<tr>
<td>${result.entYear}</td>
<td>${result.classNum}</td>
<td>${result.studentNo}</td>
<td>${result.studentName}</td>
<c:forEach begin="1" end="2" var="testNo">
<td class="text-center">
<c:set var="score" value="${result.getScore(testNo)}" />
<c:choose>
<c:when test="${not empty score}">${score}</c:when>
<c:otherwise>-</c:otherwise>
</c:choose>
</td>
</c:forEach>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</div>
</c:if>

            <!-- Student Test Results -->
<c:if test="${not empty student}">
<div class="card mb-4">
<div class="card-header bg-light py-2">
<h3 class="h5 mb-0">成績一覧（学生）: ${student.name} (${student.no})</h3>
</div>
<div class="card-body">
<table class="table table-bordered table-sm">
<thead class="bg-light">
<tr>
<th>科目名称</th>
<th>科目コード</th>
<th>回数</th>
<th>点数</th>
</tr>
</thead>
<tbody>
<c:forEach var="test" items="${studentTestResults}">
<tr>
<td>${test.subjectName}</td>
<td>${test.subjectCode}</td>
<td class="text-center">${test.testNo}</td>
<td class="text-center">${test.score}</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</div>
</c:if>

            <!-- Footer -->
<div class="text-center text-muted small mt-4">

                © 2023 TIC 大阪学園
</div>
</div>
</c:param>
</c:import>
