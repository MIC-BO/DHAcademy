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
        <h1 class="mt-5 text-right">${pageTitle}</h1>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-lg-9">
          <form:form method="post" modelAttribute="student">
              <%@ include file="templates/studentFragment.jsp" %>
              <button type="submit" class="btn btn-success float-right">Save</button>
            </form:form>
      </div>
    </div>

  </div>
  <%@ include file="templates/footer.jsp" %>
</body>

</html>