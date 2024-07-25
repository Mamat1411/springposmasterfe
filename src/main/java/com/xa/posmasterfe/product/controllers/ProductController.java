package com.xa.posmasterfe.product.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.xa.posmasterfe.category.dtos.responses.CategoryResponse;
import com.xa.posmasterfe.product.dtos.requests.ProductRequest;
import com.xa.posmasterfe.product.dtos.responses.ProductResponse;
import com.xa.posmasterfe.utilities.Api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Configuration
@Controller
@RequestMapping("/product")
public class ProductController {
    
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @GetMapping("")
    public ModelAndView getAllProducts() {
        ModelAndView view = new ModelAndView("product/index");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate().exchange(Api.API_PRODUCT, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<ProductResponse> products = (List<ProductResponse>) response.getBody().get("data");
            view.addObject("product", products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    
    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView view = new ModelAndView("product/form");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ProductResponse product = new ProductResponse();
        try {
            ResponseEntity<LinkedHashMap<String, Object>> categoryResponse = restTemplate().exchange(Api.API_CATEGORY, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<CategoryResponse> categories = (List<CategoryResponse>) categoryResponse.getBody().get("data");
            view.addObject("product", product);
            view.addObject("categories", categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute ProductRequest productRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(productRequest, headers);
        try {
            restTemplate().exchange(Api.API_PRODUCT, HttpMethod.POST, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/product");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCategory(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("product/formEdit");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> categoryResponse = restTemplate().exchange(Api.API_CATEGORY, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<CategoryResponse> categories = (List<CategoryResponse>) categoryResponse.getBody().get("data");
            ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate().exchange(Api.API_PRODUCT + "/" + id,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            ProductResponse data = modelMapper.map(response.getBody().get("data"), ProductResponse.class);
            view.addObject("product", data);
            view.addObject("categories", categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView updateCategory(@PathVariable Long id, @ModelAttribute ProductRequest productRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(productRequest, headers);
        try {
            restTemplate().exchange(Api.API_PRODUCT + "/" + id, HttpMethod.PUT, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/product");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("product/deleteForm");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            
            ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate().exchange(Api.API_PRODUCT + "/" + id,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            ProductResponse data = modelMapper.map(response.getBody().get("data"), ProductResponse.class);
            view.addObject("product", data);
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
            restTemplate().exchange(Api.API_PRODUCT + "/" + id, HttpMethod.DELETE, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/product");
    }
}
