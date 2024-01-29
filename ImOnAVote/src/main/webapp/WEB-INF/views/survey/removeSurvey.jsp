<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>remove survey</title>
</head>
<body>

	<div align="center" style="padding-top: 100px">
		<h3>설문지 삭제</h3>
		<p><span style="color:red;">삭제된 설문은 다시는 복구 할 수 없습니다. 정말 삭제하시겠습니까?</span></p>
		<form action="/survey/removeSurvey" method="post">
			<input type="hidden" name="surveyId" value="${surveyDTO.surveyId }">
			<input type="submit" value="삭제하기">
			<input type="button" value="취소" onclick="location.href='/member/main=${memberId}'">
		</form>
	</div>

</body>
</html>