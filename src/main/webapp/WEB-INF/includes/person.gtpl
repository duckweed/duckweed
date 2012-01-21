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
<%= user.getNickname()%>
<a href="logout.groovy" name="logout">log out </a><br/>
Nick Name <%= request.session.person.username %></br>
Bio <%= request.session.person.bio %></br>


<a href="newcircle.groovy" name="newcircle">create a circle</a><br/>
