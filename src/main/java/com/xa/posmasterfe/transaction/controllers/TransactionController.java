package com.xa.posmasterfe.transaction.controllers;

import java.util.LinkedHashMap;
import java.util.List;

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

import com.xa.posmasterfe.utilities.Api;
import com.xa.posmasterfe.variant.dtos.responses.VariantResponse;

import org.springframework.web.bind.annotation.GetMapping;


@Configuration
@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
    
    @GetMapping("")
    public ModelAndView transactionView() {
        ModelAndView view = new ModelAndView("transaction/index");
        view.addObject("title", "Transaction Page");
        return view;
    }

    @SuppressWarnings({ "null", "unchecked" })
    @GetMapping("/getproductvariants")
    public ModelAndView getProductVariants() {
        ModelAndView view = new ModelAndView("transaction/variants.html");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate().exchange(Api.API_MASTER_VARIANT, HttpMethod.GET, entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>(){});
            List<VariantResponse> variants = (List<VariantResponse>) response.getBody().get("data");
            view.addObject("variant", variants);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @GetMapping("/payment")
    public ModelAndView paymentView() {
        ModelAndView view = new ModelAndView("transaction/payment.html");
        return view;
    }
    
}
