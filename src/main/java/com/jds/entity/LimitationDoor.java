package com.jds.entity;

import com.jds.model.LimiItem;
import com.jds.model.modelEnum.TypeOfLimitionDoor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Limitation_Door")
public class LimitationDoor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @Column(name = "typeSettings")
    @Enumerated(EnumType.STRING)
    private TypeOfLimitionDoor typeSettings;

    @Column(name = "item_id")
    private int itemId;

    @Column(name = "firstItem")
    private String firstItem;

    @Column(name = "secondItem")
    private String secondItem;

    @Column(name = "startRestriction")
    private double startRestriction;

    @Column(name = "stopRestriction")
    private double stopRestriction;

    //@Column(name = "step")
    //private double step;

    @Column(name = "pairOfValues")
    private int pairOfValues;

    @Column(name = "comment")
    private String comment;

    @Column(name = "defaultValue")
    private int defaultValue;


    public LimitationDoor() {
    }//empty constructor

    public LimitationDoor(String firstItem, double startRestriction, double stopRestriction, int defaultValue) {
        this.firstItem = firstItem;
        this.secondItem = firstItem;
        this.startRestriction = startRestriction;
        this.stopRestriction = stopRestriction;
        this.defaultValue = defaultValue;
    }

    public LimitationDoor(String firstItem, String secondItem, double startRestriction, double stopRestriction, int defaultValue) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
        this.startRestriction = startRestriction;
        this.stopRestriction = stopRestriction;
        this.defaultValue = defaultValue;
    }

    public void setFildForSaveToDataBase(@NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                         @NonNull List<LimitationDoor> oldLimitList) {

        int newId = 0;
        if (oldLimitList.size() > 0) {
            newId = oldLimitList.get(0).getId();
            oldLimitList.remove(0);
        }

        this.id = newId;
        this.doorType = doorType;
        this.typeSettings = type;


    }

    public static LimitationDoor getNewLimit(@NonNull LimiItem item, @NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                             @NonNull List<LimitationDoor> oldLimitList) {
        int newId = 0;
        if (oldLimitList.size() > 0) {
            newId = oldLimitList.get(0).getId();
            oldLimitList.remove(0);
        }

        LimitationDoor limit = new LimitationDoor();
        limit.setId(newId);
        limit.setDoorType(doorType);
        limit.setTypeSettings(type);
        limit.setFirstItem(item.getName());
        limit.setItemId(item.getId());
        limit.setPairOfValues(0);
        return limit;
    }

    public LimitationDoor setNuulLazyFild(){
        this.doorType = null;
        return this;
    }
}
