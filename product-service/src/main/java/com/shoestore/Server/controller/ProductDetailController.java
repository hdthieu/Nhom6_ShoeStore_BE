package com.shoestore.Server.controller;


import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.dto.ProductDetailDTO;
import com.shoestore.Server.entities.Product;
import com.shoestore.Server.entities.ProductDetail;
import com.shoestore.Server.service.ProductDetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products-details")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }


    @PostMapping
    public ResponseEntity<?> addProductDetail(@Valid @RequestBody ProductDetail productDetail,
                                              BindingResult bindingResult      ) {

        System.out.println(productDetail.getColor());
        System.out.println(productDetail.getSize());
        System.out.println(productDetail.getProduct().getProductID());

        // kiểm tra tồn tại
        ProductDetail productDetail1 = productDetailService.getProductDetailByProductIdAndColorAndSize(productDetail.getProduct().getProductID(), productDetail.getColor(), productDetail.getSize());
        System.out.println(productDetail1);
        if (productDetail1 != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Đã tồn tại cặp màu sắc và size cho sản phẩm này");
        }


        if (bindingResult.hasErrors()) {
            // Trả về lỗi validation
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            System.out.println(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }


        try {
            // Lưu sản phẩm vào cơ sở dữ liệu
            ProductDetail savedProductDetail = productDetailService.addProductDetail(productDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDetail);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // chỉ cập nhật soluong
    @PutMapping("/detail/{id}")
    public ResponseEntity<?> updateProductDetail(@PathVariable int id, @Valid @RequestBody ProductDetail productDetail, BindingResult bindingResult) {
        ProductDetail productDetail1 = productDetailService.getProductDetailById(id);
        if (productDetail1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm có id = " + id);
        }
        if (bindingResult.hasErrors()) {
            // Trả về lỗi validation
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            System.out.println(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        productDetail1.setStockQuantity(productDetail.getStockQuantity());
        productDetailService.save(productDetail1);
        return ResponseEntity.ok(productDetail1);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getProductDetailsByProductId(@PathVariable int id) {
        List<ProductDetail> productDetails=productDetailService.getByProductId(id);
        Map<String,Object> response= new HashMap<>();
        response.put("productDetails",productDetails);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/productDetailId/{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetailsById(@PathVariable int id) {
        ProductDetail productDetail = productDetailService.getProductDetailById(id);
        if (productDetail == null) {
            return ResponseEntity.notFound().build();
        }

        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProductDetailID(productDetail.getProductDetailID());
        dto.setColor(productDetail.getColor().toString());
        dto.setSize(productDetail.getSize().toString());
        dto.setStockQuantity(productDetail.getStockQuantity());

        // Chỉ set productID nếu product != null
        if (productDetail.getProduct() != null) {
            dto.setProductID(productDetail.getProduct().getProductID());
        }

        return ResponseEntity.ok(dto);
    }

    //cart
    //cart
    @GetMapping("/detailWithProduct/{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetailWithProduct(@PathVariable int id) {
        ProductDetail productDetail = productDetailService.getProductDetailById(id);
        if (productDetail == null || productDetail.getProduct() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProductDetailID(productDetail.getProductDetailID());
        dto.setColor(String.valueOf(productDetail.getColor()));
        dto.setSize(String.valueOf(productDetail.getSize()));
        dto.setStockQuantity(productDetail.getStockQuantity());

        // Gán product thủ công
        Product product = productDetail.getProduct();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(product.getProductID());
        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());
        productDTO.setStatus(product.getStatus());
        productDTO.setDescription(product.getDescription());
        productDTO.setBrandName(product.getBrand().getName());
        productDTO.setCategoryName(product.getCategory().getName());
        productDTO.setCreateDate(product.getCreateDate());
//        productDTO.setImageURL(product.getImageURL());

        dto.setProduct(productDTO); // ✅ set đúng kiểu `int`


        return ResponseEntity.ok(dto);
    }

}
