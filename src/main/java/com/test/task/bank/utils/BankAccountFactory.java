package com.test.task.bank.utils;

import com.test.task.bank.domain.BankAccount;

import java.math.BigInteger;

public class BankAccountFactory {
    public static boolean beforeTransferCheck(BankAccount sender, BigInteger transferAmount) {
        return sender.getCurrentBalance().signum() >= transferAmount.signum();
    }

}
