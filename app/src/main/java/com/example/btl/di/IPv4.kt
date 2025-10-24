package com.example.btl.di

import okhttp3.Dns
import java.net.Inet4Address
import java.net.InetAddress
import java.net.UnknownHostException


class IPv4 : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        return try {
            Dns.SYSTEM.lookup(hostname).filter {
                it is Inet4Address
            }
        } catch (e: UnknownHostException) {
            throw e
        }
    }
}