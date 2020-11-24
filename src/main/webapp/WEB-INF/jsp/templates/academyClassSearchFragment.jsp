<div class="row">
  <div class="col">
    <div class="form-group">
      <label class="mr-sm-2" for="title"><small>Title</small></label>
      <form:input class="form-control mr-sm-2" autocomplete="off" path="title" placeholder="Title" />
    </div>
  </div>


  <div class="col">
    <div class="form-group">
      <label class="mr-sm-2" for="Description"><small>Description</small></label>
      <form:input class="form-control mr-sm-2" autocomplete="off" path="description" placeholder="Description" />
      <c:if test="${message != null}">
      <small class="form-text font-weight-bold text-success">${message}</small>
      </c:if>
    </div>
  </div>
</div>