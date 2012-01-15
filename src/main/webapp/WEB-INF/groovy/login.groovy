def username = params.get("username");
def password = params.get("password");

if(username == "andrew" && password == "password"){
    request.getSession(true)
    request.setAttribute 'username', 'andrew'
    forward '/welcome.gtpl'
    return
}



forward '/login.gtpl'