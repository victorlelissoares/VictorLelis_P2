package com.example.victorlelis_p2;

import java.io.Serializable;

public class Time implements Serializable {
    private int idTime;
    private String name;//nome do time

    public int getIdTime() {
        return idTime;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Time{" +
                "idTime=" + idTime +
                ", name='" + name + '\'' +
                '}';
    }
}
