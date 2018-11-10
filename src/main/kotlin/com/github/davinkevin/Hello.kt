package com.github.davinkevin

import com.fazecast.jSerialComm.SerialPort

fun main(args: Array<String>) {
    println("Hello, World")

    val ports = SerialPort.getCommPorts()

    val numberOfPorts = ports.size
    println("There is $numberOfPorts ports")

    ports.forEach {
        println("port: ${it.descriptivePortName}")
    }

    val zigate = ports
            .asSequence()
            .filter { "FT232R" in it.descriptivePortName }
            .filter { "Dial-In" !in it.descriptivePortName }
            .first() ?: throw RuntimeException("No port found")

    zigate.apply { baudRate = 115200 }

    zigate.openPort()
    zigate.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 12)
    val i = zigate.inputStream

    for (it in 0..1000) println(i.read())
}

