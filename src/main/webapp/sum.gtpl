<% include '/WEB-INF/includes/header.gtpl' %>

<h1>Sum Me</h1>

Sum is <%= request.getAttribute('sum') %>

<form>
    b:<input type="text" name="b"/><br/>
    a:<input type="text" name="a"/><br/>
    <input type="submit" name="Send"/>
</form>


<% include '/WEB-INF/includes/footer.gtpl' %>
