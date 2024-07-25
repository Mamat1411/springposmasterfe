package com.xa.posmasterfe.category.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.xa.posmasterfe.category.dtos.requests.CategoryRequest;
import com.xa.posmasterfe.category.dtos.responses.CategoryResponse;
import com.xa.posmasterfe.utilities.Api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("")
    public ModelAndView getAllCategories() {
        ModelAndView view = new ModelAndView("category/index");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> responseCategory = restTemplate().exchange(Api.API_CATEGORY,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<CategoryResponse> data = (List<CategoryResponse>) responseCategory.getBody().get("data");
            String title = "Category Page";
            view.addObject("categories", data);
            view.addObject("title", title);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return view;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView view = new ModelAndView("category/form");
        CategoryResponse category = new CategoryResponse();
        view.addObject("category", category);
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute CategoryRequest categoryRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(categoryRequest, headers);
        try {
            restTemplate().exchange(Api.API_CATEGORY, HttpMethod.POST, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/category");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCategory(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("category/formEdit");
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> responseCategory = restTemplate().exchange(Api.API_CATEGORY + "/" + id,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            CategoryResponse data = modelMapper.map(responseCategory.getBody().get("data"), CategoryResponse.class);
            view.addObject("category", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView updateCategory(@PathVariable Long id, @ModelAttribute CategoryRequest categoryRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(categoryRequest, headers);
        try {
            restTemplate().exchange(Api.API_CATEGORY + "/" + id, HttpMethod.PUT, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/category");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("category/deleteForm");
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> responseCategory = restTemplate().exchange(Api.API_CATEGORY + "/" + id,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            CategoryResponse data = modelMapper.map(responseCategory.getBody().get("data"), CategoryResponse.class);
            view.addObject("category", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @GetMapping("remove/{id}")
    public ModelAndView removeCategory(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);
        try {
            restTemplate().exchange(Api.API_CATEGORY + "/" + id, HttpMethod.DELETE, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/category");
    }
}
