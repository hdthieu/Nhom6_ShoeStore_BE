package com.shoestore.Server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.entities.*;
import com.shoestore.Server.service.BrandService;
import com.shoestore.Server.service.CategoryService;
import com.shoestore.Server.service.ProductService;
import com.shoestore.Server.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${user.dir}")  // Lấy thư mục gốc của dự án
    private String userDir;

    @Value("${file.upload-dir:uploads}")  // Mặc định là "uploads" nếu không khai báo trong properties
    private String uploadDir;

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SupplierService supplierService;


    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService, SupplierService supplierService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.supplierService = supplierService;
    }
    @GetMapping("/filtered")
    public ResponseEntity<LinkedHashMap<String, Object>> getFilteredProducts(
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) List<Integer> brandIds,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy
    ) {
        // Giải mã tham số sortBy nếu cần
        try {
            if (sortBy != null) {
                sortBy = URLDecoder.decode(sortBy, StandardCharsets.UTF_8.toString());
                System.out.println("SortBy sau khi giải mã: " + sortBy);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  // Xử lý lỗi nếu giải mã thất bại
        }

        // Tiến hành lấy dữ liệu sản phẩm với các tham số đã giải mã
        List<Product> products = productService.getFilteredProducts(categoryIds, brandIds, colors, sizes, minPrice, maxPrice, sortBy);

        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        // Debug dữ liệu trước khi trả về
        System.out.println("Dữ liệu trước khi trả về: " + products);
        System.out.println("Số lượng sản phẩm: " + products.size());

        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping // Ánh xạ HTTP GET
    public ResponseEntity<Map<String,Object>> getAllProducts(){
        List<Product> products=productService.getAllProduct();
        Map<String,Object> response= new HashMap<>();
        response.put("products",products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/add")
    public ResponseEntity<Map<String,Object>> getCategories(){
        Map<String,Object> response= new HashMap<>();
        response.put("categories",categoryService.getAllCategory());
        response.put("brands",brandService.getAllBrand());
        response.put("suppliers",supplierService.getAllSupplier());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestParam("image") MultipartFile[] files,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("status") String status,
            @RequestParam("brandID") int brandID,
            @RequestParam("categoryID") int categoryID,
            @RequestParam("supplierID") int supplierID,
            @Valid @ModelAttribute Product product,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        try {
            if (productName == null || productName.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên sản phẩm không được để trống.");
            }

            if (files.length == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng tải lên ít nhất một ảnh sản phẩm.");
            }

            // Tạo đường dẫn tuyệt đối ngang hàng product-service
            Path parentDir = Paths.get(System.getProperty("user.dir")).getParent(); // ../Nhom6_ShoeStore_BE
            Path uploadPath = parentDir.resolve(uploadDir).resolve("images");       // ../Nhom6_ShoeStore_BE/uploads/images

            // Tạo thư mục nếu chưa tồn tại
            File directory = uploadPath.toFile();
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo thư mục lưu ảnh.");
                }
            }

            List<String> imageUrls = saveUploadedFiles(files, uploadPath);

            //Tạo sản phẩm
            product = new Product();
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setStatus(status);
            product.setBrand(new Brand(brandID));
            product.setCategory(new Category(categoryID));
            product.setSupplier(new Supplier(supplierID));
            product.setCreateDate(LocalDateTime.now());
            product.setImageURL(imageUrls);

            Product savedProduct = productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi xử lý ảnh.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi không xác định: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.ok("Sản phẩm đã được xóa thành công");  // Trả về thông báo thành công
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm để xóa");  // Trả về thông báo lỗi nếu không tìm thấy sản phẩm
        }
    }

    @GetMapping("/detailFor/{id}")
    public ResponseEntity<Product> getProductByIdForDetail(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable int id,
            @RequestParam(value = "image", required = false) MultipartFile[] files,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") String priceStr,
            @RequestParam("status") String status,
            @RequestParam("brandID") int brandID,
            @RequestParam("categoryID") int categoryID,
            @RequestParam("supplierID") int supplierID,
            @Valid @ModelAttribute Product existingProduct, BindingResult bindingResult
    ) {
        double price = 0;
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giá sản phẩm không hợp lệ.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giá sản phẩm không được rỗng.");
        }

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        try {
            // Lấy sản phẩm hiện tại từ ID
            existingProduct = productService.getProductById(id);
            if (existingProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm.");
            }

            // Cập nhật thông tin sản phẩm
            existingProduct.setProductName(productName);
            existingProduct.setDescription(description);
            existingProduct.setPrice(price);
            existingProduct.setStatus(status);
            existingProduct.setBrand(new Brand(brandID));
            existingProduct.setCategory(new Category(categoryID));
            existingProduct.setSupplier(new Supplier(supplierID));
            existingProduct.setCreateDate(existingProduct.getCreateDate()); // giữ nguyên ngày tạo

            // Nếu có ảnh được tải lên
            // Nếu có ảnh mới được upload
            if (files != null && files.length > 0) {
                Path parentDir = Paths.get(System.getProperty("user.dir")).getParent(); // ../Nhom6_ShoeStore_BE
                Path uploadPath = parentDir.resolve(uploadDir).resolve("images");

                List<String> imageUrls = saveUploadedFiles(files, uploadPath);
                existingProduct.setImageURL(imageUrls);
            } else {
                // Không có ảnh mới => giữ nguyên ảnh cũ
                existingProduct.setImageURL(existingProduct.getImageURL());
            }


            // Lưu thay đổi vào CSDL
            Product savedProduct = productService.saveProduct(existingProduct);

            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu ảnh.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi.");
        }
    }





    // API lấy danh sách Best Sellers
//    @GetMapping("/best-sellers")
//    public ResponseEntity<Map<String, Object>> getBestSellers() {
//        List<ProductDTO> bestSellers = productService.getTop10BestSellers();
//        System.out.println(bestSellers);
//        Map<String, Object> response = new HashMap<>();
//        response.put("bestSellers", bestSellers);
//        return ResponseEntity.ok(response);
//    }
//
//    // API lấy danh sách New Arrivals
//    @GetMapping("/new-arrivals")
//    public ResponseEntity<Map<String, Object>> getNewArrivals() {
//        List<ProductDTO> newArrivals = productService.getTop10NewArrivals();
//        Map<String, Object> response = new HashMap<>();
//        System.out.println(newArrivals);
//        response.put("newArrivals", newArrivals);
//        return ResponseEntity.ok(response);
//    }
//
//    // API lấy danh sách Trending
//    @GetMapping("/trending")
//    public ResponseEntity<Map<String, Object>> getTrendingProducts() {
//        List<ProductDTO> trendingProducts = productService.getTop10Trending();
//        System.out.println(trendingProducts);
//        Map<String, Object> response = new HashMap<>();
//        response.put("trendingProducts", trendingProducts);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/findproducts")
    public ResponseEntity<Map<String, Object>> findProducts(
            @RequestParam(required = false) String keyword, // Cho phép null
            @RequestParam(defaultValue = "0") int page, // Trang mặc định là 0
            @RequestParam(defaultValue = "5") int size, // Kích thước mặc định là 5
            @RequestParam(required = false) String sortBy, // Có thể null
            @RequestParam(required = false) String order) {

        // Giải mã giá trị của keyword, chuyển %20 thành dấu cách (nếu có)
        if (keyword != null) {
            keyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        }

        System.out.println("Keyword: " + keyword);

        // Tạo đối tượng Pageable để phân trang
        Pageable pageable = PageRequest.of(page, size);

        // Gọi phương thức service để lấy kết quả
        Page<Product> productPage = productService.findProducts(keyword, sortBy, order, pageable);

        // Chuẩn bị response
        Map<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());
        response.put("currentPage", page);

        return ResponseEntity.ok(response);
    }



    // Upload ảnh
    private List<String> saveUploadedFiles(MultipartFile[] files, Path uploadPath) throws IOException {
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now().format(formatter);

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();

            // Lấy tên không có đuôi mở rộng
            String baseName = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                    : originalFileName;

            // Lấy phần mở rộng (.jpg, .png, ...)
            String extension = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf('.'))
                    : "";

            // Tạo chuỗi random
            String randomString = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);

            // Tạo tên file mới theo định dạng: ngày + tên gốc + random + đuôi
            String newFileName = date + "_" + baseName + "_" + randomString + extension;

            // Lưu file
            Path filePath = uploadPath.resolve(newFileName);
            file.transferTo(filePath.toFile());

            imageUrls.add(newFileName);
        }

        return imageUrls;
    }

}
