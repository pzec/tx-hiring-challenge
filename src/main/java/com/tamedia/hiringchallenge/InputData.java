package com.tamedia.hiringchallenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Receives data from input-topic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputData {
    private String uid;
    private long ts;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
