package ru.sqwk.ssn.websocket;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ru.sqwk.ssn.util.SecurityContextWrapper;

import java.security.Principal;
import java.util.Map;

@Slf4j
public class UserHandshakeHandler extends DefaultHandshakeHandler {
  @Override
  protected Principal determineUser(
      ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
    // log.info(request.getPrincipal().getName());
    log.info(SecurityContextWrapper.getNowUser().getUsername());
    return new UserPrincipal(SecurityContextWrapper.getNowUser().getUsername());
  }
}
