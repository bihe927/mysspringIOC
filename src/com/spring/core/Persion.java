package com.spring.core;

public class Persion {
    private int pid;
    private String pname;

    @Override
    public String toString() {
        return "Persion{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                '}';
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Persion() {

    }

    public Persion(int pid, String pname) {
        this.pid = pid;
        this.pname = pname;
    }
}
