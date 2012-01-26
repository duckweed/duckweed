<html>
<head>
    <title>Duckweed</title>

    <link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
    <link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
</head>
<body>
<div class='guff'>
    <a class="home-button" href="/">duckweed</a>

    <% if (user != null){%>
    <a href="/showprofile.groovy" name="profile">profile</a>
    <a href="logout.groovy" name="logout">log out </a>
    <%}else{%>
    <a href="login.groovy" name='login'>log in</a>
    <%}%>

</div>
<div class='guts'