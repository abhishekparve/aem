package com.bdb.aem.core.bean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListBlogsBean implements Comparable<ListBlogsBean>{

    /** The date. */
    private String date;

    /** The title. */
    private String title;

    /** The Blog link. */
    private String blogLink;

    Logger logger = LoggerFactory.getLogger(ListBlogsBean.class);


    @Override
    public int compareTo(ListBlogsBean o) {
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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }


}
