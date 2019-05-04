package com.jds.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jds.dao.MainDAO;
import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringReader;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    MainDAO mainDAO;

    @GetMapping(value = "/")
    public String updateDoorClass(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson) throws Exception {

        return "hello2";
    }
    @GetMapping(value = "/setting")
    public String setting(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = mainDAO.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass";
    }
    @GetMapping(value = "/doorclass")
    public String getDoorClass(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = mainDAO.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass";
    }
    @GetMapping(value = "/doortype")
    public String getDoorType(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorType> list = mainDAO.getDoorType();
        model.addAttribute("accountInfos", list);
        return "settingPage_doortype";
    }
}
