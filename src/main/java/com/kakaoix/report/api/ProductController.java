package com.kakaoix.report.api;

import com.kakaoix.report.domain.Product;
import com.kakaoix.report.model.DefaultRes;
import com.kakaoix.report.model.Pagination;
import com.kakaoix.report.service.ProductService;
import com.kakaoix.report.utils.ResponseMessage;
import com.kakaoix.report.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ds on 2018-08-31.
 */

@RestController
@RequestMapping("products")
public class ProductController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<DefaultRes<Iterable<Product>>> getAllProducts(
            @RequestParam(value = "offset", defaultValue = "0", required = false) final int offset,
            @RequestParam(value = "limit", defaultValue = "10", required = false) final int limit,
            @RequestParam(value = "sort", defaultValue = "productIdx", required = false) final String sort
    ) {
        try {
            final Pagination pagination = Pagination.builder().offset(offset).limit(limit).sort(new Sort(Sort.Direction.DESC, sort)).build();
            return new ResponseEntity<DefaultRes<Iterable<Product>>>(productService.findAll(pagination), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<DefaultRes<Iterable<Product>>>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{product_idx}")
    public ResponseEntity<DefaultRes<Product>> getProduct(@PathVariable final int product_idx) {
        try {
            return new ResponseEntity<DefaultRes<Product>>(productService.findOne(product_idx), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<DefaultRes<Product>>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
