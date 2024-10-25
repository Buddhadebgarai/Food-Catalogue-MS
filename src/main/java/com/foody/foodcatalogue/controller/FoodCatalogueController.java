package com.foody.foodcatalogue.controller;

import com.foody.foodcatalogue.dto.FoodCataloguePage;
import com.foody.foodcatalogue.dto.FoodItemDTO;
import com.foody.foodcatalogue.service.FoodCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foodCatalogue")
@CrossOrigin
public class FoodCatalogueController {

    @Autowired
    private FoodCatalogueService foodCatalogueService;

    @PostMapping("/addFoodItem")
    public ResponseEntity<FoodItemDTO> addFoodItem(@RequestBody  FoodItemDTO foodItemDTO)
    {
        FoodItemDTO savedFoodItem = foodCatalogueService.addFoodItem(foodItemDTO);
        return new ResponseEntity<>(savedFoodItem, HttpStatus.CREATED);
    }

    @GetMapping("/getFoodCataloguePageById/{id}")
    public ResponseEntity<FoodCataloguePage> getFoodCataloguePageById(@PathVariable Integer id)
    {
        FoodCataloguePage foodCataloguePage = foodCatalogueService.getFoodCatalogueById(id);
        return  new ResponseEntity<>(foodCataloguePage,HttpStatus.OK);
    }

}
