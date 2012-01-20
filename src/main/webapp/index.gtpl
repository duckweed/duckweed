<% include '/WEB-INF/includes/header.gtpl' %>

<%import java.util.List%>
<%import com.google.appengine.api.users.User%>
<%import com.google.appengine.api.users.UserService%>
<%import com.google.appengine.api.users.UserServiceFactory%>
<%import com.google.appengine.api.datastore.DatastoreServiceFactory%>
<%import com.google.appengine.api.datastore.DatastoreService%>
<%import com.google.appengine.api.datastore.Query%>
<%import com.google.appengine.api.datastore.Entity%>
<%import com.google.appengine.api.datastore.FetchOptions%>
<%import com.google.appengine.api.datastore.Key%>
<%import com.google.appengine.api.datastore.KeyFactory%>

<div class="main">

    <h1>Welcome to Duckweed Collaboration</h1>
<%
request.getSession(true)
if(user != null && session != null && session.getAttribute('person') != null ){
    Entity person = session.getAttribute('person')
    def username = person.getProperty('username')
    def bio = person.getProperty('bio')
%>
<%= user.getNickname()%>
<a href="logout.groovy">log out </a><br/>
Nick Name <%= username %></br>
Bio <%= bio %></br>
<a href="/showprofile.groovy">profile</a>
<%
}else{
%>
<a href="login.groovy">log in</a>
<%
}
%>

</div>

<% include '/WEB-INF/includes/demos.gtpl' %>
<% include '/WEB-INF/includes/footer.gtpl' %>

