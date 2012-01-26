<%import java.util.List%>
<%import com.google.appengine.api.datastore.Query%>
<%import com.google.appengine.api.datastore.Entity%>

<div class="actionbar">

<%
Entity person = request.session.getAttribute('person')
if(person == null){
    def id = user.getUserId()
    def query = new Query('person')
    query.addFilter("id", Query.FilterOperator.EQUAL, id)
    person = datastore.prepare(query).asSingleEntity();
    request.session.setAttribute('person', person);
}
%>
<a href="newcircle.groovy" name="newcircle">create a circle</a>
<a href="showcircles.groovy" name="showcircles">show circles</a>
<%= user.getNickname()%>
<%= request.session.person.username %>
<%= request.session.person.bio %>

</div>

