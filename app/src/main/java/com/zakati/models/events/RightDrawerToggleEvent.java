package com.zakati.models.events;

/**
 * Created by Rahil on 11/15/2015.
 *
 */
public class RightDrawerToggleEvent {
    private boolean isDrawerOpened;

    public RightDrawerToggleEvent(boolean isDrawerOpened) {
        this.isDrawerOpened = isDrawerOpened;
    }

    public boolean isDrawerOpened() {
        return isDrawerOpened;
    }

    public void setIsDrawerOpened(boolean isDrawerOpened) {
        this.isDrawerOpened = isDrawerOpened;
    }
}
