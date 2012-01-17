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

<h1>Welcome to Duckweed Collaboration</h1>

<%
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();



if(user != null){
%>
Welcome <%= user.getNickname()%>  <a href="deauthenticate.groovy">log off here</a><br/>
Auth Domain = <%= user.getAuthDomain()%><br/>
Federated Identity = <%= user.getFederatedIdentity()%><br/>
User Id = <%= user.getUserId()%><br/>

<%
}else{
%>
<a href="authenticate.groovy">log on</a>
<%
}
%>

hello, here

<p>
    Click <a href="login.groovy">here</a> to log in to this app (beta: username/password, andrew/password)<br/>
    Click <a href="authenticate.groovy">here</a> new style log on<br/>
    Click <a href="deauthenticate.groovy">here</a> new style log off<br/>
    Click <a href="datetime.groovy">here</a> to view the current date/time, when it's a good time to collaborate.<br/>
    Click <a href="sum.groovy">here</a> to view the add 2 numbers!!
</p>

<p>
    <a target="1" href="http://duckweedcollaboration.appspot.com/">live</a><br/>
    <a target="2" href="https://appengine.google.com/dashboard?&app_id=s~duckweedcollaboration">google app console</a><br/>
    <a target="3" href="https://github.com/duckweed/duckweed">github</a><br/>
</p>

<% include '/WEB-INF/includes/footer.gtpl' %>

