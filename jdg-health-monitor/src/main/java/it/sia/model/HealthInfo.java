package it.sia.model;

/**
 * Health Stats Info Details
 * 
 * @author Stelios Kousouris
 * 
 */
public class HealthInfo {
    
    private double WRITE_LATENCY;
    private double READ_LATENCY;
    private int MAX_HOTROD_THREADS;
    private int MAX_HOTROD_THREADS_ACTIVE;
    private int MAX_HOTROD_THREADS_POOL_SIZE;
    private int MAX_HOTROD_THREADS_USAGE_PEAK; // since starting (ie. it can be reset)

    public double getWRITE_LATENCY() {
        return WRITE_LATENCY;
    }

    public void setWRITE_LATENCY(double wRITE_LATENCY) {
        WRITE_LATENCY = wRITE_LATENCY;
    }

    public double getREAD_LATENCY() {
        return READ_LATENCY;
    }

    public void setREAD_LATENCY(double rEAD_LATENCY) {
        READ_LATENCY = rEAD_LATENCY;
    }

    public int getMAX_HOTROD_THREADS() {
        return MAX_HOTROD_THREADS;
    }

    public void setMAX_HOTROD_THREADS(int mAX_HOTROD_THREADS) {
        MAX_HOTROD_THREADS = mAX_HOTROD_THREADS;
    }

    public int getMAX_HOTROD_THREADS_ACTIVE() {
        return MAX_HOTROD_THREADS_ACTIVE;
    }

    public void setMAX_HOTROD_THREADS_ACTIVE(int mAX_HOTROD_THREADS_ACTIVE) {
        MAX_HOTROD_THREADS_ACTIVE = mAX_HOTROD_THREADS_ACTIVE;
    }

    public int getMAX_HOTROD_THREADS_POOL_SIZE() {
        return MAX_HOTROD_THREADS_POOL_SIZE;
    }

    public void setMAX_HOTROD_THREADS_POOL_SIZE(int mAX_HOTROD_THREADS_POOL_SIZE) {
        MAX_HOTROD_THREADS_POOL_SIZE = mAX_HOTROD_THREADS_POOL_SIZE;
    }

    public int getMAX_HOTROD_THREADS_USAGE_PEAK() {
        return MAX_HOTROD_THREADS_USAGE_PEAK;
    }

    public void setMAX_HOTROD_THREADS_USAGE_PEAK(int mAX_HOTROD_THREADS_USAGE_PEAK) {
        MAX_HOTROD_THREADS_USAGE_PEAK = mAX_HOTROD_THREADS_USAGE_PEAK;
    }

    
}