package com.zup.exception

import java.lang.RuntimeException

class ValorDuplicadoException(message: String = "Valor duplicado no sistema"): RuntimeException(message)