package com.peredera.songs.user.web;

import com.peredera.songs.user.domain.User;
import com.peredera.songs.user.dto.AuthenticationRequest;
import com.peredera.songs.user.dto.AuthenticationResponse;
import com.peredera.songs.user.dto.RegisterRequest;
import com.peredera.songs.user.service.UserServiceBean;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserServiceBean userService;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PutMapping(value = "/addFavourite/{id}", params = {"song_id"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> addToFavourite(@PathVariable("id") Long id, @RequestParam Long song_id) {
        return ResponseEntity.ok(userService.addToFavourite(id, song_id));
    }

}
