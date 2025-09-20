package ipwa02.ghostnetfishing.controller;

import ipwa02.ghostnetfishing.entity.*;
import ipwa02.ghostnetfishing.model.*;
import ipwa02.ghostnetfishing.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GhostNetRestControllerTest {

    private GhostNetRepository ghostNetRepository;
    private UserRepository userRepository;
    private GhostNetRestController controller;

    private final UUID userId = UUID.randomUUID();
    private final UUID ghostNetId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        ghostNetRepository = mock(GhostNetRepository.class);
        userRepository = mock(UserRepository.class);
        controller = new GhostNetRestController(ghostNetRepository);
    }






}
