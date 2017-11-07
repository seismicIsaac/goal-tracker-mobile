package com.iaustin.goaltracker.event;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by iaustin on 5/18/2015.
 */
public class Signal implements ISignal, Json.Serializable {

    ArrayList<ICallback> callbacks = new ArrayList<ICallback>();

    public void dispatch(Object optional)
    {
        for(ICallback callback : callbacks)
        {
            callback.execute(optional);
        }
    }

    @Override
    public void addCallback(ICallback callback)
    {
        callbacks.add(callback);
    }

    @Override
    public void removeCallback(ICallback callback)
    {
        if(callbacks.contains(callback))
            callbacks.remove(callback);
    }

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }
}
