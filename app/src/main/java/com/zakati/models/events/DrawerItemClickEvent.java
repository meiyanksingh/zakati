package com.zakati.models.events;

/**
 * Created by rahil on 15/1/16.
 */
public class DrawerItemClickEvent {
    int pos;

    public DrawerItemClickEvent(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
