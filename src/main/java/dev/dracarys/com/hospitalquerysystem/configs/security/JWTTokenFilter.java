package dev.dracarys.com.hospitalquerysystem.configs.security;

import dev.dracarys.com.hospitalquerysystem.configs.DetailsUserData;
import dev.dracarys.com.hospitalquerysystem.dominio.Role;
import dev.dracarys.com.hospitalquerysystem.dominio.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JWTTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);


        try{
            setAuthenticationContext(token, request);
        } catch (ExpiredJwtException ignored) {
        }
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer");
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private UserDetails getUserDetails(String token) {
        UserModel userDetails = new UserModel();
        Claims claims = JwtTokenUtil.parseClaims(token);
        String username = (String) claims.get("sub");
        String roles = (String) claims.get("roles");

        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");

        for (String aRoleName : roleNames) {
            userDetails.addRole(new Role(aRoleName));
        }

        userDetails.setUsername(username);


        return new DetailsUserData(Optional.of(userDetails));
    }
}
