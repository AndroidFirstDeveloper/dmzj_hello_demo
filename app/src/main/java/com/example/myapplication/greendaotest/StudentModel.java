package com.example.myapplication.greendaotest;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class StudentModel {

    @Id
    private Long id;
    private String name;
    private int age;
    private String address;
    private String birth;

    @Generated(hash = 1216722002)
    public StudentModel(Long id, String name, int age, String address,
            String birth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.birth = birth;
    }

    @Generated(hash = 2060229341)
    public StudentModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
