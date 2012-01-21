<% include '/WEB-INF/includes/header.gtpl' %>

<%import java.util.List%>
<%import com.google.appengine.api.datastore.Query%>
<%import com.google.appengine.api.datastore.Entity%>

<div class="main">
    <h1>Welcome to Duckweed Collaboration</h1>
<%
    if(user != null){
    Entity person = request.session.getAttribute('person')
    if(person == null){
        def id = user.getUserId()
        def query = new Query('user')
        query.addFilter("id", Query.FilterOperator.EQUAL, id)
        person = datastore.prepare(query).asSingleEntity();
        request.session.setAttribute('person', person);
    }
    def username = person.getProperty('username')
    def bio = person.getProperty('bio')
    %>
    <%= user.getNickname()%>
    <a href="logout.groovy" name="logout">log out </a><br/>
    Nick Name <%= request.session.person.username %></br>
    Bio <%= request.session.person.bio %></br>
    <%
}else{
%>
<a href="login.groovy" name='login'>log in</a>
<%
}
%>

</div>

<% include '/WEB-INF/includes/demos.gtpl' %>
<% include '/WEB-INF/includes/footer.gtpl' %>

