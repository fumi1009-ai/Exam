
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="m-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            <div class="border m-3 p-3 rounded">
                <!-- 科目情報 Search Form -->
                <form method="get" action="TestListSubjectExecute.action">
                    <div class="row g-3 align-items-center mb-3">
                        <div class="col-md-2 d-flex align-items-center">
                            <p class="mb-0">科目情報</p>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label" for="filter-f1-select">入学年度</label>
                            <select class="form-select" id="filter-f1-select" name="f1">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label" for="filter-f2-select">クラス</label>
                            <select class="form-select" id="filter-f2-select" name="f2">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label" for="filter-f3-select">科目</label>
                            <select class="form-select" id="filter-f3-select" name="f3">
                                <option value="0">--------</option>
                                <c:forEach var="subject" items="${subject_set}">
                                    <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3 d-flex align-items-end justify-content-center">
                            <button class="btn btn-secondary" id="filter-button-subject">検索</button>
                        </div>
                    </div>
                </form>

                <div class="mt-3 text-warning">${errors.get("filter")}</div>

                <hr class="my-3">

                <!-- 学生情報 Search Form -->
                <form method="get" action="TestListStudentExecute.action">
                    <div class="row g-3 align-items-center">
                        <div class="col-md-2 d-flex align-items-center">
                            <label class="mb-0">学生情報</label>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label" for="filter-f4-input">学生番号</label>
                            <input type="text" id="filter-f4-input" name="f4" class="form-control" value="${f4}" placeholder="学生番号を入力してください">
                        </div>
                        <div class="col-md-4 d-flex align-items-center justify-content-center">
                            <button class="btn btn-secondary" id="filter-button-student">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <%-- ===== 結果表示エリア ===== --%>
            <c:choose>
                <%-- === 科目検索結果の表示 === --%>
                <c:when test="${testResults == 'subject'}">
                    <c:choose>
                        <c:when test="${subjectTests != null && subjectTests.size()>0}">
                            <div class="card mt-3">
                                <div class="card-header bg-light">
                                    <c:if test="${selectedSubject != null}">
                                        検索科目：<strong>${selectedSubject.name}</strong>
                                    </c:if>
                                </div>
                                <div class="card-body p-0">
                                    <table class="table table-hover mb-0">
                                        <thead class="bg-light">
                                            <tr>
                                                <th>入学年度</th>
                                                <th>クラス</th>
                                                <th>学生番号</th>
                                                <th>氏名</th>
                                                <th class="text-center">１回</th>
                                                <th class="text-center">２回</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="subjectTests" items="${subjectTests}">
                                                <tr>
                                                    <td>${subjectTests.entYear}</td>
                                                    <td>${subjectTests.classNum}</td>
                                                    <td>${subjectTests.studentNo}</td>
                                                    <td>${subjectTests.studentName}</td>
                                                    <td class="text-center">
                                                        <c:choose>
                                                            <c:when test="${subjectTests.points != null && subjectTest.points[1] != null && subjectTest.points[1] != 0}">
                                                                ${subjectTests.points[1]}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="text-center">
                                                        <c:choose>
                                                            <c:when test="${subjectTest.points != null && subjectTest.points[2] != null && subjectTest.points[2] != 0}">
                                                                ${subjectTest.points[2]}
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-info mt-3">成績情報が存在しませんでした。</div>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <%-- === 学生検索結果の表示 === --%>
                <c:when test="${resultType == 'student'}">
                    <div class="mt-3 text-warning">${errors.get("student_search")}</div>
                    <c:if test="${selectedStudent != null}">
                        <div class="card mt-3">
                            <div class="card-header bg-light">
                                氏名：<strong>${selectedStudent.name} (${selectedStudent.no})</strong>
                            </div>
                            <div class="card-body p-0">
                                <c:choose>
                                    <c:when test="${studentTests != null && studentTests.size()>0}">
                                        <table class="table mb-0">
                                            <thead class="bg-light">
                                                <tr>
                                                    <th>科目名</th>
                                                    <th>科目コード</th>
                                                    <th class="text-center">回数</th>
                                                    <th class="text-center">点数</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="test" items="${studentTests}">
                                                    <tr>
                                                        <td>${test.subjectName}</td>
                                                        <td>${test.subjectCd}</td>
                                                        <td class="text-center">${test.num}</td>
                                                        <td class="text-center">
                                                            <c:choose>
                                                                <c:when test="${test.point != null && test.point != 0}">
                                                                    ${test.point}
                                                                </c:when>
                                                                <c:otherwise>-</c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-info">成績情報が存在しませんでした。</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:if>
                </c:when>

                <c:otherwise>
                    <div class="alert alert-info mt-3">検索条件を指定してください。</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>

