package com.example.kinoxpbackend.admin;

import com.example.kinoxpbackend.movie.Movie;
import com.example.kinoxpbackend.movie.MovieService;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    public AdminService() {

    }

    public boolean getAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

}
