package com.majorpotato.steampowered.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface PacketUser {

    public void writeData(DataOutputStream data) throws IOException;
    public void readData(DataInputStream data) throws IOException ;
}
