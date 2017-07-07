package com.whisper.whispme.helpers;

/**
 * Created by NightmareTK on 05/07/2017.
 */

import java.util.UUID;

public class UUIDGeneratorHelper {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}
