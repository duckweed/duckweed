import com.google.appengine.api.users.UserServiceFactory

request.setAttribute 'logouturl', UserServiceFactory.getUserService().createLogoutURL('/')

forward '/logout.gtpl'