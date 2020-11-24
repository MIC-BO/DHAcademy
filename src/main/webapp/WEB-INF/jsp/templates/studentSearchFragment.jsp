<div class="row">
  <div class="col">
    <div class="form-group">
      <label class="mr-sm-2" for="firstName"><small>First Name</small></label>
      <form:input class="form-control mr-sm-2" autocomplete="off" path="firstName" placeholder="First Name" />
    </div>
  </div>


  <div class="col">
    <div class="form-group">
      <label class="mr-sm-2" for="Last Name"><small>Last Name</small></label>
      <form:input class="form-control mr-sm-2" autocomplete="off" path="lastName" placeholder="Last Name" />
      <c:if test="${message != null}">
      <small class="form-text font-weight-bold text-success">${message}</small>
      </c:if>
    </div>
  </div>
</div>