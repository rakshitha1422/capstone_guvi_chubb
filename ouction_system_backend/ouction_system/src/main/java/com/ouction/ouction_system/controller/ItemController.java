package com.ouction.ouction_system.controller;

import com.ouction.ouction_system.model.Item;
import com.ouction.ouction_system.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController

@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/fetch")
    public List<Item> fetchAll(){
        List<Item> res=itemService.getAllItems();
        return res;
    }

    @PostMapping("/save")
    public ResponseEntity<Item> saveItem(@RequestBody Item item){


            Item savedItem = itemService.saveItem(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);

    }


}
