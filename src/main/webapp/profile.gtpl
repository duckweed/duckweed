<% include '/WEB-INF/includes/header.gtpl' %>

<h1>New User Details</h1>
<form action="profile.groovy">
    Nick Name <input type="text" name="username" value="<%= session.person.username%>"/></br>
    Bio<textarea name="bio" rows="4" cols="50"><%= session.person.bio%></textarea><br/>
    <input type="submit" name="Send"/>
</form>
<% include '/WEB-INF/includes/footer.gtpl' %>
