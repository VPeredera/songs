package com.peredera.songs.user.service;

import com.peredera.songs.user.dto.AuthenticationRequest;
import com.peredera.songs.user.dto.AuthenticationResponse;
import com.peredera.songs.user.dto.RegisterRequest;

public interface UserService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
