package com.example.m21219.logintest;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Hakan Akkurt on 03.02.2017.
 */

public class ToDo implements Serializable {
    private long id;
    private String name;
    private Calendar completiondate;
    private Calendar completiontime;
    private boolean favorite;
    private String   description;
    private boolean completionstatus;


    public ToDo() {
        this(null, null, null, false, null, false);
    }

    public ToDo(final String name) {
        this(name, null, null, false, null, false);
    }



    public ToDo(final String name, final Calendar completiondate, final Calendar completiontime, final boolean favorite, final String description, final boolean completionstatus) {
        this.name = name;
        this.completiondate = completiondate;
        this.completiontime = completiontime;
        this.favorite = favorite;
        this.description = description;
        this.completionstatus = completionstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Calendar getCompletiondate() {
        return completiondate;
    }

    public void setCompletiondate(final Calendar completiondate) {
        this.completiondate = completiondate;
    }

    public Calendar getCompletiontime() {
        return completiontime;
    }

    public void setCompletiontime(Calendar completiontime) {
        this.completiontime = completiontime;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isCompletionstatus() {
        return completionstatus;
    }

    public void setCompletionstatus(final boolean completionstatus) {
        this.completionstatus = completionstatus;
    }
}



