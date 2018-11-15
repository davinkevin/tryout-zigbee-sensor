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
    //zigate.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 12)
    //val i = zigate.inputStream

//
//    var buffer = mutableListOf<Int>()
//    while (true) {
//        val elem = i.read()
//        if (elem == 0) {
//            continue
//        }
//
//        buffer.add(elem)
//
//        if(elem == 3) {
//            println(buffer)
//            buffer = mutableListOf()
//        }
//    }
//
//    decode(testFrame)

//    while (true) {
//        while (zigate.bytesAvailable() == 0)
//            Thread.sleep(20)
//
//        val readBuffer = ByteArray(zigate.bytesAvailable())
//        val numRead = zigate.readBytes(readBuffer, readBuffer.size.toLong())
//        println("Read $numRead bytes.")
//        println("Value is $readBuffer")
//    }
}

val testFrame = listOf(1, 129, 2, 18, 2, 16, 2, 31, 216, 74, 120, 160, 2, 17, 2, 20, 2, 18, 2, 16, 2, 16, 2, 16, 41, 2, 16, 2, 18, 2, 26, 75, 171, 3)

fun decode(buffer: List<Int>): List<Int> {
    for (c in 0..buffer.size step 2) {
        //println("${buffer[c]} ${buffer[c+1]}")
        val first = buffer[c]
        if(first == 2) {
            val realValue = buffer[c+1] xor 0x10
            println("$realValue")
        } else {
            println("$first")
        }

    }
    return buffer
}

