package com.tamedia.hiringchallenge;

/**
 * Sent to output-topic
 */
public class OutputData {
    double averageUsersPerMinute;
    long minOccurancesPerUid;
    long maxOccurancesPerUid;
    double lowerErrorBound;
    double upperErrorBound;

    public double getLowerErrorBound() {
        return lowerErrorBound;
    }

    public void setLowerErrorBound(double lowerErrorBound) {
        this.lowerErrorBound = lowerErrorBound;
    }

    public double getUpperErrorBound() {
        return upperErrorBound;
    }

    public void setUpperErrorBound(double upperErrorBound) {
        this.upperErrorBound = upperErrorBound;
    }

    public double getAverageUsersPerMinute() {
        return averageUsersPerMinute;
    }

    public void setAverageUsersPerMinute(double averageUsersPerMinute) {
        this.averageUsersPerMinute = averageUsersPerMinute;
    }

    public long getMinOccurancesPerUid() {
        return minOccurancesPerUid;
    }

    public void setMinOccurancesPerUid(long minOccurancesPerUid) {
        this.minOccurancesPerUid = minOccurancesPerUid;
    }

    public long getMaxOccurancesPerUid() {
        return maxOccurancesPerUid;
    }

    public void setMaxOccurancesPerUid(long maxOccurancesPerUid) {
        this.maxOccurancesPerUid = maxOccurancesPerUid;
    }
}
