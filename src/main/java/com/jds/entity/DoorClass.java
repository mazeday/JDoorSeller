package com.jds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Door_Class")
public class DoorClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "fireproof", nullable = false)
    private int fireproof;

    @Column(name = "hot", nullable = false)
    private int hot;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doorClass", cascade = CascadeType.ALL)
    private List<DoorType> doorTypes;

    public DoorClass() {
        //empty constructor
    }

    public DoorClass clearNonSerializingFields(){

        for(DoorType doorType:doorTypes){
            doorType.clearNonSerializingFields();
        }

        return this;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFireproof() {
        return fireproof;
    }

    public void setFireproof(int fireproof) {
        this.fireproof = fireproof;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public List<DoorType> getDoorTypes() {
        return doorTypes;
    }

    public void setDoorTypes(List<DoorType> doorTypes) {
        this.doorTypes = doorTypes;
    }
}
