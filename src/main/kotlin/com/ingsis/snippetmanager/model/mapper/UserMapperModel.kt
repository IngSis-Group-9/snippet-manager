package com.ingsis.snippetmanager.model.mapper

import com.ingsis.snippetmanager.model.bo.UserBO
import com.ingsis.snippetmanager.model.de.UserDE

class UserMapperModel {
    fun convertUserBOToDE(userBO: UserBO): UserDE {
        return UserDE(userBO.getUsername())
    }

    fun convertUserDEToBo(userDE: UserDE): UserBO {
        return UserBO(userDE.getUsername())
    }
}
