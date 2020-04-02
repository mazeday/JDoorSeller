package com.jds.dao.entity;

import com.jds.model.AvailableFieldsForSelection;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.SerializingFields;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "FurnitureKit")
public class FurnitureKit implements SerializingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "topLock")
    private DoorFurniture topLock;

    @ManyToOne()
    @JoinColumn(name = "topinternaLockDecoration")
    private DoorFurniture topinternaLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "topouterLockDecoration")
    private DoorFurniture topouterLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "toplockCylinder")
    private DoorFurniture topLockCylinder;

    @ManyToOne()
    @JoinColumn(name = "lowerLock")
    private DoorFurniture lowerLock;

    @ManyToOne()
    @JoinColumn(name = "lowerinternaLockDecoration")
    private DoorFurniture lowerinternaLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "lowerouterLockDecoration")
    private DoorFurniture lowerouterLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "lowerlockCylinder")
    private DoorFurniture lowerLockCylinder;

    @ManyToOne()
    @JoinColumn(name = "handle")
    private DoorFurniture handle;

    @ManyToOne()
    @JoinColumn(name = "closer")
    private DoorFurniture closer;

    @ManyToOne()
    @JoinColumn(name = "endDoorLock")
    private DoorFurniture endDoorLock;

    @Column(name = "nightLock")
    private int nightLock;

    @Column(name = "peephole")
    private int peephole;

    @Column(name = "amplifierCloser")
    private int amplifierCloser;

    @OneToOne(mappedBy = "furnitureKit",fetch = FetchType.LAZY)
    private DoorEntity door;


    public boolean exists(){

        if ((topLock!=null)||(lowerLock!=null)||(handle!=null)||(closer!=null)){
            return true;
        }
        return false;
    }

    public static FurnitureKit instanceKit(@NonNull RestrictionOfSelectionFields template){
        FurnitureKit kit = new FurnitureKit();
        kit.setTopLock(convertLimToShortFurniture(template.getTopLock()));
        kit.setLowerLock(convertLimToShortFurniture(template.getLowerLock()));
        kit.setHandle(convertLimToShortFurniture(template.getHandle()));
        return kit;
    }

    public static FurnitureKit instanceKit(@NonNull AvailableFieldsForSelection AvailableFields){
        FurnitureKit kit = new FurnitureKit();
        kit.setTopLock(getFirst(AvailableFields.getTopLock()));
        kit.setLowerLock(getFirst(AvailableFields.getLowerLock()));
        kit.setHandle(getFirst(AvailableFields.getHandle()));
        return kit;
    }

    private static DoorFurniture getFirst(List<DoorFurniture> furnitureList){
        if(furnitureList.size()>0){
            return furnitureList.get(0);
        }
        return null;
    }

    public static DoorFurniture convertLimToShortFurniture(@NonNull List<LimitationDoor> listLim){

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {

            return new DoorFurniture(defList.get(0));
        }

        return null;
    }

    public FurnitureKit clearNonSerializingFields(){

        door = null;

        if(topLock!=null){
            topLock.setNuulLazyFild();
        }
        if(topinternaLockDecoration!=null){
            topinternaLockDecoration.setNuulLazyFild();
        }
        if(topouterLockDecoration!=null){
            topouterLockDecoration.setNuulLazyFild();
        }

        if(topLockCylinder !=null){
            topLockCylinder.setNuulLazyFild();
        }



        if(lowerLock!=null){
            lowerLock.setNuulLazyFild();
        }

        if(lowerinternaLockDecoration!=null){
            lowerinternaLockDecoration.setNuulLazyFild();
        }
        if(lowerouterLockDecoration!=null){
            lowerouterLockDecoration.setNuulLazyFild();
        }

        if(lowerLockCylinder !=null){
            lowerLockCylinder.setNuulLazyFild();
        }

        if(handle!=null){
            handle.setNuulLazyFild();
        }
        if(closer!=null){
            closer.setNuulLazyFild();
        }
        if(endDoorLock!=null){
            endDoorLock.setNuulLazyFild();
        }
        return this;
    }

    public DoorFurniture getCloser() {
        return closer;
    }

    public void setCloser(DoorFurniture closer) {
        this.closer = closer;
    }

    public DoorFurniture getTopLock() {
        return topLock;
    }

    public void setTopLock(DoorFurniture topLock) {
        this.topLock = topLock;
    }

    public DoorFurniture getLowerLock() {
        return lowerLock;
    }

    public void setLowerLock(DoorFurniture lowerLock) {
        this.lowerLock = lowerLock;
    }

    public DoorFurniture getHandle() {
        return handle;
    }

    public void setHandle(DoorFurniture handle) {
        this.handle = handle;
    }

    public DoorFurniture getLowerinternaLockDecoration() {
        return lowerinternaLockDecoration;
    }

    public void setLowerinternaLockDecoration(DoorFurniture lowerinternaLockDecoration) {
        this.lowerinternaLockDecoration = lowerinternaLockDecoration;
    }

    public DoorFurniture getLowerouterLockDecoration() {
        return lowerouterLockDecoration;
    }

    public void setLowerouterLockDecoration(DoorFurniture lowerouterLockDecoration) {
        this.lowerouterLockDecoration = lowerouterLockDecoration;
    }

    public DoorFurniture getTopinternaLockDecoration() {
        return topinternaLockDecoration;
    }

    public void setTopinternaLockDecoration(DoorFurniture topinternaLockDecoration) {
        this.topinternaLockDecoration = topinternaLockDecoration;
    }

    public DoorFurniture getTopouterLockDecoration() {
        return topouterLockDecoration;
    }

    public void setTopouterLockDecoration(DoorFurniture topouterLockDecoration) {
        this.topouterLockDecoration = topouterLockDecoration;
    }

    public DoorFurniture getTopLockCylinder() {
        return topLockCylinder;
    }

    public void setTopLockCylinder(DoorFurniture topLockCylinder) {
        this.topLockCylinder = topLockCylinder;
    }

    public DoorFurniture getLowerLockCylinder() {
        return lowerLockCylinder;
    }

    public void setLowerLockCylinder(DoorFurniture lowerLockCylinder) {
        this.lowerLockCylinder = lowerLockCylinder;
    }

    public int getNightLock() {
        return nightLock;
    }

    public void setNightLock(int nightLock) {
        this.nightLock = nightLock;
    }

    public int getPeephole() {
        return peephole;
    }

    public void setPeephole(int peephole) {
        this.peephole = peephole;
    }

    public int getAmplifierCloser() {
        return amplifierCloser;
    }

    public void setAmplifierCloser(int amplifierCloser) {
        this.amplifierCloser = amplifierCloser;
    }

    public DoorFurniture getEndDoorLock() {
        return endDoorLock;
    }

    public void setEndDoorLock(DoorFurniture endDoorLock) {
        this.endDoorLock = endDoorLock;
    }

    public DoorEntity getDoor() {
        return door;
    }

    public void setDoor(DoorEntity door) {
        this.door = door;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}