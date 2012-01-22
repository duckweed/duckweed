<% include '/WEB-INF/includes/header.gtpl' %>

<div class="main">
    <h1>Welcome to Duckweed Collaboration</h1>
<% if(user != null){ %>
    <% include '/WEB-INF/includes/person.gtpl' %>
    <% } %>

</div>

<% include '/WEB-INF/includes/demos.gtpl' %>
<% include '/WEB-INF/includes/footer.gtpl' %>

