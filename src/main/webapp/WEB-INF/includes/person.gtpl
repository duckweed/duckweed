<%import java.util.List%>
<%import com.google.appengine.api.datastore.Query%>
<%import com.google.appengine.api.datastore.Entity%>

<%
Entity person = request.session.getAttribute('person')
if(person == null){
    def id = user.getUserId()
    def query = new Query('user')
    query.addFilter("id", Query.FilterOperator.EQUAL, id)
    person = datastore.prepare(query).asSingleEntity();
    request.session.setAttribute('person', person);
}
%>
<a href="newcircle.groovy" name="newcircle">create a circle</a>
<a href="showcircles.groovy" name="showcircles">show circles</a><br/>
<%= user.getNickname()%><BR/>
Nick Name <%= request.session.person.username %></br>
Bio <%= request.session.person.bio %></br>


