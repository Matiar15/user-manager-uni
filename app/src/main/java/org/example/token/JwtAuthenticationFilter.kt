package org.example.token

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenService: TokenService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader.notContainsBearer()) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader!!.extractTokenValue()
        val email = tokenService.extractEmail(jwtToken)

        if (email != null && SecurityContextHolder.getContext().authentication == null) {

            if (tokenService.isValid(jwtToken)) {
                val authToken = updateToken(email, request)
                SecurityContextHolder.getContext().authentication = authToken
            }

            filterChain.doFilter(request, response)
        }
    }

    private fun updateToken(email: String, request: HttpServletRequest): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, null, null)
            .apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }
    }

    private fun String?.notContainsBearer() =
        this == null || !this.startsWith("Bearer ")

}

