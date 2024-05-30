package com.example.krisapp;

import java.util.List;

/**
 * Interface för att visa meddelanden.
 * @Author Filip Claesson
 */
public interface MessageDisplayer {
    void displayMessages(List<VMAMessageObject> messages);
}