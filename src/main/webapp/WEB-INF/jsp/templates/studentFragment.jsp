  <form:input type="hidden" path="studentID" />
  <div class="form-group">
    <label for="firstName"><small>First Name</small></label>
    <form:input class="form-control" autocomplete="off" path="firstName" placeholder="First Name" />
  </div>
  <div class="form-group">
    <label for="Last Name"><small>Last Name</small></label>
    <form:input class="form-control" autocomplete="off" path="lastName" placeholder="Last Name" />
    <c:if test="${message != null}">
    <small class="form-text font-weight-bold text-success">${message}</small>
    </c:if>
  </div>