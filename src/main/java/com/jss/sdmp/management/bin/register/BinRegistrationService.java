package com.jss.sdmp.management.bin.register;


public interface BinRegistrationService {

    void register(String username, BinRegisterDto binRegisterDto);

    void activate(String bin);

    boolean checkStatus(String bin);
}
