package org.example.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.example.exception.UserNotFoundInTokenException
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

interface TokenService {
    fun isValid(token: String): Boolean

    fun extractEmail(token: String): String?

    fun getClaims(token: String): Claims

    fun getUserId(token: String): Int
}

@Service
class TokenServiceImpl(
    jwtConfig: JwtConfig
) : TokenService {
    val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtConfig.key.toByteArray())

    override fun getClaims(token: String): Claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .run {
            parseSignedClaims(token).payload
        }

    override fun getUserId(token: String): Int {
        return getClaims(token.extractTokenValue())["userId"].toString().toIntOrNull()
            ?: throw UserNotFoundInTokenException()
    }

    override fun extractEmail(token: String): String? = getClaims(token).subject

    fun isNotExpired(token: String) = getClaims(token).expiration.after(Date())

    override fun isValid(token: String): Boolean = extractEmail(token)
        ?.let { isNotExpired(token) } ?: false
}

fun String.extractTokenValue() = substringAfter("Bearer ")
