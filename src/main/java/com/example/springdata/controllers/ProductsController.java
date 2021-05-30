package com.example.springdata.controllers;

import com.example.springdata.entities.Product;
import com.example.springdata.entities.User;
import com.example.springdata.repositories.UserRepository;
import com.example.springdata.repositories.specifications.ProductsSpecs;
import com.example.springdata.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductsController {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    private ProductsService productsService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showItems(Principal principal, Model model,
                            @RequestParam(value="page", required = false) Integer page,
                            @RequestParam(value="word",required = false)String word,
                            @RequestParam(value="minPrice",required = false)BigDecimal minPrice,
                            @RequestParam(value="maxPrice",required = false)BigDecimal maxPrice ) {
        // check the email of registered user with "Principal"
        if (principal!=null) {
            User user = userRepository.findOneByUsername(principal.getName());
            model.addAttribute("username", user.getName());
            System.out.println( user.getId()+" "+user.getEmail());
        }
        if (page==null) page=1;
        Specification<Product> filter =Specification.where(null);
        if (word!=null){
            filter= filter.and(ProductsSpecs.titleContains(word));
        }
        if (minPrice!=null){
            filter= filter.and(ProductsSpecs.priceGreaterThanOrEq(minPrice));
        }
        if (maxPrice!=null){
            filter= filter.and(ProductsSpecs.priceLesserThanOrEq(maxPrice));
        }


        List<Product> resultList =productsService.
                getProductsWithPagingAndFiltering(filter, PageRequest.of(page-1,5))
                .getContent();
        model.addAttribute("products", resultList);
        model.addAttribute("top3List", productsService.getTop3List());
        model.addAttribute("word", word);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "products";
    }
    @GetMapping("/show/{id}")
    public String show(Model model, @PathVariable (value="id") Long id) {
        Product product= productsService.incrementViewAndReturnProduct(id);
        model.addAttribute("product", product);
        return "product-page";
    }
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable(value="id") Long id){
        productsService.deleteById(id);
        return "redirect:/products";
    }
    @Secured(value="ROLE_ADMIN")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        Product product= new Product();
        model.addAttribute("product", product);
        return "product-edit";
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping("/edit/{id}")
    public String showEditProductForm(Model model, @PathVariable(value="id") Long id) {
        Product product = productsService.findById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }

    @Secured(value="ROLE_ADMIN")
    @PostMapping("/edit")
    public String addProduct(@ModelAttribute(value= "product") Product product) {
        productsService.saveOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/{id}")
    @ResponseBody //означает что тот объект который мы возвращаем завернется в json  и полетит клиенту
    public Product getProductById(@PathVariable(value = "id") Long id) {

        return productsService.findById(id);
    }

    //for postman
//    @RequestMapping(RequestMethod.DELETE)


    @DeleteMapping(value = "/{id}")
    public int deleteProductById(@PathVariable(value = "id") Long id) {
        productsService.deleteById(id);
        return 200;
    }
}
