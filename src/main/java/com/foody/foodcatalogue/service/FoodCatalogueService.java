package com.foody.foodcatalogue.service;

import com.foody.foodcatalogue.dto.FoodCataloguePage;
import com.foody.foodcatalogue.dto.FoodItemDTO;
import com.foody.foodcatalogue.dto.RestaurantDTO;
import com.foody.foodcatalogue.entity.FoodItem;
import com.foody.foodcatalogue.mapper.FoodItemMapper;
import com.foody.foodcatalogue.repo.FoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FoodCatalogueService {

    @Autowired
    private FoodItemRepo foodItemRepo;

    @Autowired
    private RestTemplate restTemplate;

    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {

        FoodItem saveFoodItem = foodItemRepo.save(FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO));
        return  FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(saveFoodItem);
    }

    public FoodCataloguePage getFoodCatalogueById(Integer restaurantId) {
        // fooditem list
        List<FoodItem>  foodItemList = fetchFoodItemList(restaurantId);
        RestaurantDTO restaurantDTO = fetchRestaurantDetails(restaurantId);
        

        return createFoodCataloguePage(foodItemList,restaurantDTO);
    }

    private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, RestaurantDTO restaurantDTO) {
        FoodCataloguePage foodCataloguePage = new FoodCataloguePage();
        foodCataloguePage.setFoodItemDTOList(foodItemList.stream().map(FoodItemMapper.INSTANCE::mapFoodItemToFoodItemDto).toList());
        foodCataloguePage.setRestaurantDTO(restaurantDTO);
        return  foodCataloguePage;
    }

    private RestaurantDTO fetchRestaurantDetails(Integer restaurantId) {
        return restTemplate.getForObject("http://RESTAURANT-SERVICE/restaurant/getRestaurantById/"+restaurantId,RestaurantDTO.class);
    }

    private List<FoodItem> fetchFoodItemList(Integer restaurantId) {

        return  foodItemRepo.findByRestaurantId(restaurantId);
    }
}
