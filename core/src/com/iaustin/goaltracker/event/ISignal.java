package com.iaustin.goaltracker.event;

/**
 * Created by iaustin on 7/9/2015.
 */
public interface ISignal {
    public void addCallback(ICallback callback);
    public void removeCallback(ICallback callback);
}
