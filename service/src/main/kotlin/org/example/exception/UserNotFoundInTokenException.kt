package org.example.exception

import org.example.exception.generic.NotFoundException


class UserNotFoundInTokenException : NotFoundException("Provided token does not contain user")