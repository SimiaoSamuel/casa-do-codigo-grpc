package com.zup.exception

import java.lang.RuntimeException

class NaoExisteException(message: String = "Objeto não existente no sistema"): RuntimeException(message)  {
}