package org.example.auction

import jakarta.validation.Valid
import org.example.token.TokenService
import org.example.token.extractTokenValue
import org.example.util.EntityMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/my/auctions")
class MyAuctionController(
    private val auctionService: AuctionService,
    private val mapper: EntityMapper,
    private val tokenService: TokenService
) {

    @GetMapping
    fun fetchByFilter(
        @Valid filter: AuctionFilterRequest,
        page: Pageable,
        @RequestHeader(AUTHORIZATION) token: String
    ): Page<AuctionResponse> {
        val userId: Int = tokenService.getUserId(token.extractTokenValue())
        return auctionService.fetchByFilter(filter.asFilter(userId), page).map(mapper::fromAuction)
    }
}