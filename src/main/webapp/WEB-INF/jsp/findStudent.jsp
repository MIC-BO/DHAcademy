<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<%@ include file="templates/head.jsp" %>

<body>
  <%@ include file="templates/menu.jsp" %>

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="mt-5 text-right">Find Students</h1>
      </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <h6 class="mt-5 text-right"><c:if test="${navigationMessage != null}">${navigationMessage}</c:if></h6>
        </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-lg-9">
          <form:form method="post" action="${action}" modelAttribute="student">
              <%@ include file="templates/studentSearchFragment.jsp" %>
              <button type="submit" class="btn btn-success float-right">Find</button>
          </form:form>
      </div>
    </div>

    <c:if test="${students != null}">
      <div class="row">
        <div class="col-lg-12">
          <h1 class="mt-5 text-right">Results</h1>
        </div>
      </div>
      <%@ include file="templates/listOfStudents.jsp" %>
    </c:if>
  </div>
  <%@ include file="templates/footer.jsp" %>

</body>

</html>
