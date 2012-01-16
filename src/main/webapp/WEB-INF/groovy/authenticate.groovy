import com.google.appengine.api.users.User
import com.google.appengine.api.users.UserServiceFactory
import com.google.appengine.api.users.UserService

UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();

if (user != null) {
    forward '/'
} else {
    response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
}
