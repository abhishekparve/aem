package com.bdb.aem.core.bean;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class ListEventsBean to get the event details for the List component.
 */
public class ListEventsBean implements Comparable<ListEventsBean> {

    /**
     * The date.
     */
    private String date;

    /**
     * The location.
     */
    private String location;

    /**
     * The title.
     */
    private String title;

    /**
     * The event link.
     */
    private String eventLink;


    /**
     * Gets the date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the event link.
     *
     * @return the event link
     */
    public String getEventLink() {
        return eventLink;
    }

    /**
     * Sets the event link.
     *
     * @param eventLink the new event link
     */
    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(ListEventsBean o) {
        String o1DateString = getDate();
        String o2DateString = o.getDate();
        if (StringUtils.isEmpty(o1DateString)) {
            o1DateString = "July 01, 1900";
        }
        if (StringUtils.isEmpty(o2DateString)) {
            o2DateString = "July 01, 1900";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
        try {
            Date o1date = sdf.parse(o1DateString);
            Date o2Date = sdf.parse(o2DateString);
            return o1date.compareTo(o2Date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
