<% include '/WEB-INF/includes/header.gtpl' %>

<h1>New User Details</h1>
<form action="profile.groovy">
    Nick Name <input type="text" name="username" value="<%= request.session.person.username%>"/></br>
    Bio<textarea name="bio" rows="4" cols="50"><%= session.person.bio%></textarea><br/>
    <input name="submit" type="submit" name="Send"/>
</form>

<% include '/WEB-INF/includes/footer.gtpl' %>
