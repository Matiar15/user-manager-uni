package org.example.token

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("jwt")
class JwtConfig {
    lateinit var key: String
    var expiresAfter: Long = 0
    var refreshAfter: Long = 0
}
