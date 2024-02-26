package Controller;

import Model.User;
import Service.UserService;
import Utili.DTO.LoginCreds;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {

    User sessionUser = null;
    private final UserService userService;
    Javalin app;
    public UserController(Javalin app, UserService userService) {
        this.app = app;
        this.userService = userService;
    }

    public void userEndpoint(Javalin app) {
        app.post("register", this::postRegisterHandler);
        app.post("login", this::postLoginHandler);
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LoginCreds loginCreds = mapper.readValue(context.body(), LoginCreds.class);
        if (sessionUser != null) {
            context.status(400);
            context.json("Successfully logged in");
        } else {
            User user = userService.loginUser(loginCreds);
            sessionUser = user;
            if (sessionUser != null) {
                context.status(200);
                context.json(user);
            } else {
                context.status(400);
                context.json("User not logged in!!!!");
            }
        }
    }

    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(context.body(), User.class);
        if (sessionUser != null) {
            context.status(400);
            context.json("The user already logged in");
        } else {
            user = userService.registerUser(user);
            if (user == null) {
                context.status(400);
                context.json("The user was not registered");
            } else {
                context.status(201);
                context.json(user);
            }
        }
    }
}
