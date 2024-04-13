package org.example.exception

import org.example.exception.generic.NotFoundException

class ItemNotAuctionedException(auctionId: Int) : NotFoundException("Item for auction ID: $auctionId was not found")
