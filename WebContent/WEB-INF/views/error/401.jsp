<%@ page contentType="text/html;charset=UTF-8"%>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>401 - 未授权</title>
</head>

<body>
	<h2>401 - 未授权访问此资源.</h2>
	<p><a href="<c:url value="/"/>">返回首页</a></p>
</body>
</html>