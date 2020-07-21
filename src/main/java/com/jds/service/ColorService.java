package com.jds.service;

import com.jds.dao.repository.ColorRepository;
import com.jds.dao.entity.ImageEntity;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorService {

    @Autowired
    private ColorRepository dAO;

    public List<ImageEntity> getColors() {
        return dAO.getImages();
    }

    public ImageEntity getColor(@NonNull String id) {

        if ("0".equals(id)) {
            return new ImageEntity();
        }

        return dAO.getColorById(Integer.parseInt(id)).clearNonSerializingFields();
    }

    public String saveColor(@NonNull ImageEntity colors) {
        String colorPath = colors.getPicturePath();
        String colorDirectory = getPathDirectoryByImageType(colors.getTypeOfImage());
        if (colorPath != null && !colorPath.contains(colorDirectory)) {
            colors.setPicturePath(colorDirectory + addFileExtension(colorPath, FileExtensionByImageType(colors.getTypeOfImage())));
        }
        dAO.saveColors(colors);
        return "ok";
    }

    public String deleteColor(@NonNull String id) {
        ImageEntity color = getColor(id);
        return dAO.deleteColor(color);
    }

    public EnumSet<TypeOfImage> getImageTypeList() {
        return EnumSet.allOf(TypeOfImage.class);
    }

    public EnumSet<TypeOfDoorColor> getImageTypeDoorColor() {
        return EnumSet.allOf(TypeOfDoorColor.class);
    }

    public String addFileExtension(@NonNull String colorPath, @NonNull String extension) {

        if (colorPath.contains(extension)) {
            return colorPath;
        }

        return colorPath + extension;
    }

    private String FileExtensionByImageType(@NonNull TypeOfImage type){
        if (TypeOfImage.DOOR_COLOR == type || TypeOfImage.SHIELD_COLOR == type)
            return ".jpg";
        else if (TypeOfImage.SHIELD_DESIGN == type)
            return ".png";
        return "";
    }
    private String getPathDirectoryByImageType(@NonNull TypeOfImage type) {
        if (TypeOfImage.DOOR_COLOR == type)
            return "images/Door/AColor1/";
        else if (TypeOfImage.SHIELD_COLOR == type)
            return "images/shield sketch/";
        else if (TypeOfImage.SHIELD_DESIGN == type)
            return "images/shield sketch/design/";
        else if (TypeOfImage.DOOR_DESIGN == type)
            return "images/Door/design/";
        return "";
    }

    public List<String> getImageList(String type){

        String pathImageDir = getPathDirectoryByImageType(TypeOfImage.valueOf(type));
        Path rootLocation = Paths.get("");
        File dir = new File (rootLocation+"src/main/resources/static/"+pathImageDir);
        if(!dir.exists()){
            System.out.println("!EROR: file is not defaund"+dir.getAbsolutePath());
        }


//        List<String> list = Arrays.stream(dir.listFiles())
//                .map(file -> pathImageDir + file.getName())
//                .collect(Collectors.toList());

        List<String> list = new ArrayList<>();
        for (File elem : dir.listFiles()){
            if (elem.isDirectory()){
                File dirParent = new File (rootLocation+"src/main/resources/static/"+pathImageDir+elem.getName());
                for (File elem2:dirParent.listFiles()){
                    list.add(pathImageDir + elem2.getName());
                }
            } else if (elem.isFile()) {
                list.add(pathImageDir + elem.getName());
            }
        }

        return list;
    }
}
