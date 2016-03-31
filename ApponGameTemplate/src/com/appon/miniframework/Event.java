/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.miniframework;

/**
 *
 * @author Swaroop Kumar
 */
public class Event {
    Control source;
    private int eventId;
    Object eventData;
    public Event(int eventId ,Control source, Object eventData) {
        this.source = source;
        this.eventId = eventId;
        this.eventData = eventData;
    }

    public Object getEventData() {
        return eventData;
    }

    public void setEventData(Object eventData) {
        this.eventData = eventData;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Control getSource() {
        return source;
    }

    public void setSource(Control source) {
        this.source = source;
    }

}
