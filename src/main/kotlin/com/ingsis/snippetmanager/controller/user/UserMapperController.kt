package com.ingsis.snippetmanager.controller.user

import com.ingsis.snippetmanager.model.bo.UserBO

class UserMapperController {
    fun convertUserTOToBO(userTO: UserTO): UserBO {
        return UserBO(userTO.getUsername())
    }

    fun convertUserBOToTO(userBO: UserBO): UserTO {
        return UserTO(userBO.getUsername())
    }
}
