package com.xa.posmasterfe.variant.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.xa.posmasterfe.category.dtos.responses.CategoryResponse;
import com.xa.posmasterfe.product.dtos.responses.ProductResponse;
import com.xa.posmasterfe.utilities.Api;
import com.xa.posmasterfe.variant.dtos.requests.VariantRequest;
import com.xa.posmasterfe.variant.dtos.responses.VariantResponse;

@Configuration
@Controller
@RequestMapping("/variant")
public class VariantController {
    
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @SuppressWarnings({ "null", "unchecked" })
    @GetMapping("")
    public ModelAndView getAllVariants() {
        ModelAndView view = new ModelAndView("variant/index");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate().exchange(Api.API_MASTER_VARIANT, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<VariantResponse> variants = (List<VariantResponse>) response.getBody().get("data");
            view.addObject("title", "Variant Page");
            view.addObject("variant", variants);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    
    @SuppressWarnings({ "null", "unchecked" })
    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView view = new ModelAndView("variant/form");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        VariantResponse variant = new VariantResponse();
        try {
            ResponseEntity<LinkedHashMap<String, Object>> categoryResponse = restTemplate().exchange(Api.API_MASTER_CATEGORY, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<CategoryResponse> categories = (List<CategoryResponse>) categoryResponse.getBody().get("data");
            view.addObject("variant", variant);
            view.addObject("categories", categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute VariantRequest variantRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(variantRequest, headers);
        try {
            restTemplate().exchange(Api.API_MASTER_VARIANT, HttpMethod.POST, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/variant");
    }

    @SuppressWarnings({ "null", "unchecked" })
    @GetMapping("/edit/{id}")
    public ModelAndView editCategory(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("variant/formEdit");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> categoryResponse = restTemplate().exchange(Api.API_MASTER_CATEGORY, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<CategoryResponse> categories = (List<CategoryResponse>) categoryResponse.getBody().get("data");
            ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate().exchange(Api.API_MASTER_VARIANT + "/" + id,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            ResponseEntity<LinkedHashMap<String, Object>> productResponse = restTemplate().exchange(Api.API_MASTER_PRODUCT, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<ProductResponse> products = (List<ProductResponse>) productResponse.getBody().get("data");
            VariantResponse data = modelMapper.map(response.getBody().get("data"), VariantResponse.class);
            view.addObject("variant", data);
            view.addObject("categories", categories);
            view.addObject("products", products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView updateCategory(@PathVariable Long id, @ModelAttribute VariantRequest variantRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(variantRequest, headers);
        try {
            restTemplate().exchange(Api.API_MASTER_VARIANT + "/" + id, HttpMethod.PUT, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/variant");
    }

    @SuppressWarnings({ "null" })
    @GetMapping("/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("variant/deleteForm");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate().exchange(Api.API_MASTER_VARIANT + "/" + id,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            VariantResponse data = modelMapper.map(response.getBody().get("data"), VariantResponse.class);
            view.addObject("variant", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @GetMapping("remove/{id}")
    public ModelAndView removeCategory(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);
        try {
            restTemplate().exchange(Api.API_MASTER_VARIANT + "/" + id, HttpMethod.DELETE, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ModelAndView("redirect:/variant");
    }
}
