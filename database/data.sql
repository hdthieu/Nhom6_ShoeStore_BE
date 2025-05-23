﻿﻿--ShoeStore_OrderService
use ShoeStore_OrderService
INSERT INTO Orders (feeShip, orderDate, shippingAddress, status,[userID] , [voucherID],[paymentID])
VALUES
    (50000.00, '2025-04-01', '123 Le Loi, Hanoi', 'Shipped', 5, 1, 0),
    (50000.00, '2025-04-02', '456 Hoang Hoa Tham, Hanoi', 'Processing', 4, 2, 0),
    (0.00, '2025-04-03', '789 Nguyen Chi Thanh, Hanoi', 'Delivered', 6, 3, 0),
    (50000.00, '2025-04-04', '12 Hai Ba Trung, Ho Chi Minh', 'Shipped', 4, 1, 0),
    (50000.00, '2025-04-05', '34 Nguyen Hue, Ho Chi Minh', 'Processing', 5, 1, 0),
    (50000.00, '2025-04-06', '56 Tran Phu, Da Nang', 'Shipped', 6, 2, 0),
    (50000.00, '2025-04-07', '78 Dien Bien Phu, Da Nang', 'Delivered', 7, 2, 0),
    (50000.00, '2025-04-08', '90 Vo Van Kiet, Can Tho', 'Processing', 8, 2, 0),
    (50000.00, '2025-04-09', '11 Nguyen Trai, Hai Phong', 'Shipped', 6, 1, 0),
    (50000.00, '2025-04-10', '22 Hai Ba Trung, Hai Phong', 'Delivered', 10, 1, 0),
    (50000.00, '2025-04-11', '33 Nguyen Cong Tru, Hue', 'Processing', 11, 1, 0),
    (50000.00, '2025-04-12', '44 Le Loi, Hue', 'Shipped', 12, 2, 0),
    (50000.00, '2025-04-13', '55 Tran Phu, Nha Trang', 'Processing', 13, 1, 0),
    (50000.00, '2025-04-14', '66 Nguyen Thien Thuat, Nha Trang', 'Delivered', 14, 3, 0),
    (50000.00, '2025-04-15', '77 Tran Hung Dao, Vung Tau', 'Processing', 14, 1, 0),
    (50000.00, '2025-04-16', '88 Nguyen Trai, Vung Tau', 'Shipped', 4, 1, 0),
    (50000.00, '2025-04-17', '99 Le Hong Phong, Da Lat', 'Delivered', 5, 1, 0),
    (50000.00, '2025-04-18', '10 Nguyen Van Cu, Da Lat', 'Processing', 6, 0, 0),
    (50000.00, '2025-04-19', '20 Tran Hung Dao, Quy Nhon', 'Shipped', 7, 1, 0),
    (50000.00, '2025-04-20', '30 Vo Thi Sau, Quy Nhon', 'Delivered', 8, 1, 0),
    (50000.00, '2025-04-21', '123 Le Loi, Hanoi', 'Shipped', 9, 2, 0),
    (50000.00, '2025-04-22', '456 Hoang Hoa Tham, Hanoi', 'Processing', 10, 3, 0),
    (50000.00, '2025-04-23', '789 Nguyen Chi Thanh, Hanoi', 'Delivered', 8, 1, 0),
    (50000.00, '2025-04-24', '12 Hai Ba Trung, Ho Chi Minh', 'Processing', 4, 1, 0),
    (50000.00, '2025-04-25', '34 Nguyen Hue, Ho Chi Minh', 'Delivered', 5, 2, 0),
    (50000.00, '2025-04-26', '56 Tran Phu, Da Nang', 'Shipped', 6, 0, 0),
    (50000.00, '2025-04-27', '78 Dien Bien Phu, Da Nang', 'Processing', 7, 3, 0),
    (50000.00, '2025-04-28', '90 Vo Van Kiet, Can Tho', 'Delivered', 8, 1, 0),
    (50000.00, '2025-04-29', '11 Nguyen Trai, Hai Phong', 'Shipped', 9, 1, 0),
    (50000.00, '2025-04-30', '22 Hai Ba Trung, Hai Phong', 'Processing', 10, 0, 0);

INSERT INTO OrderDetail (price, quantity, productDetail, orderID)
VALUES
(900000.00, 2, 1, 1), -- Nike Air Max 270
(1200000.00, 1, 2, 2), -- Adidas Harden Vol. 5
(150000.00, 3, 3, 3), -- Timberland Premium
(800000.00, 4, 4, 4), -- Converse Chuck Taylor
(1000000.00, 2, 5, 5), -- Clarks Originals
(1100000.00, 1, 6, 6), -- Asics Gel-Resolution
(750000.00, 5, 7, 7), -- Vans Old Skool
(950000.00, 3, 8, 8), -- Brooks Ghost 14
(500000.00, 1, 9, 9), -- Teva Hurricane XLT2
(200000.00, 2, 10, 10), -- Jimmy Choo Romy 100
(850000.00, 4, 11, 11), -- New Balance 574
(120000.00, 1, 12, 12), -- Under Armour HOVR
(130000.00, 2, 13, 13), -- Columbia Bugaboot
(100000.00, 5, 14, 14), -- Saucony Endorphin
(900000.00, 3, 15, 15), -- Sperry Top-Sider
(750000.00, 1, 16, 16), -- Reebok Floatride
(550000.00, 2, 17, 17), -- Keds Champion
(1100000.00, 3, 18, 18), -- Salomon Speedcross
(600000.00, 1, 19, 19), -- Xero Shoes Z-Trail
(2500000.00, 4, 20, 20), -- Gucci Ace Sneakers
(700000.00, 2, 21, 21), -- Skechers Go Walk
(140000.00, 3, 22, 22), -- Puma Clyde All-Pro
(1600000.00, 5, 23, 23), -- North Face Vectiv
(750000.00, 1, 24, 24), -- DC Shoes Trase
(130000.00, 2, 25, 25), -- Allen Edmonds Park Ave
(1150000.00, 4, 26, 26), -- Fila Axilus 2 Energized
(900000.00, 1, 27, 27), -- Lacoste Carnaby Evo
(1250000.00, 3, 28, 28), -- Hoka Speedgoat
(450000.00, 2, 29, 29), -- Merrell Hydro MOC
(220000.00, 1, 30, 30), -- Manolo Blahnik BB
(1000000.00, 2, 31, 1), -- On Cloud 5
(1500000.00, 5, 32, 2), -- Jordan Retro 1
(180000.00, 1, 33, 3), -- Sorel Caribou
(700000.00, 3, 34, 4), -- Toms Alpargata
(1900000.00, 4, 35, 5), -- Tods Gommino
(1300000.00, 2, 36, 6), -- Arcteryx Norvan
(750000.00, 3, 37, 7), -- Globe Sabre
(140000.00, 5, 38, 8), -- Altra Lone Peak
(500000.00, 2, 39, 9), -- Chaco Z/Cloud
(800000.00, 4, 40, 10), -- Christian Louboutin So Kate
(600000.00, 1, 41, 11), -- Skechers Flex Appeal
(1300000.00, 3, 42, 12), -- Nike Zoom Freak
(1700000.00, 5, 43, 13), -- Scarpa Zodiac Plus
(950000.00, 2, 44, 14), -- Adidas Superstar
(2500000.00, 4, 45, 15); -- Santoni Double Monk


--ShoeStore_ProductService
use ShoeStore_ProductService

INSERT INTO Voucher (description, discountType, discountValue, endDate, name, startDate, status, minValueOrder)
VALUES
('Welcome Voucher', 'Percentage', 10, '2025-05-05', 'WELCOME10', '2025-03-23', 'Upcoming', 35000),
('Holiday Discount', 'Flat', 20, '2025-08-10', 'HOLIDAY20', '2025-02-10', 'Active', 25000),
('Year-End Sale', 'Percentage', 15, '2025-12-31', 'YEAR15', '2025-12-15', 'Ended', 25000);
-- Thêm dữ liệu cho bảng Brand
INSERT INTO Brand (name)
VALUES
('Nike'),
('Adidas'),
('Puma'),
('Reebok'),
('Vans');

-- Thêm dữ liệu cho bảng Category
INSERT INTO Category (description, name)
VALUES
('Shoes for running and sports', 'Running'),
('Shoes for casual wear', 'Lifestyle'),
('Shoes for basketball players', 'Basketball'),
('Shoes for football players', 'Football'),
('Shoes for gym and training', 'Training'),
('High-quality tennis shoes', 'Tennis'),
('Shoes for hiking and trekking', 'Hiking'),
('Specialized soccer shoes', 'Soccer'),
('Shoes for kids and youth', 'Kids'),
('Luxury brand shoes', 'Luxury');

-- Thêm dữ liệu cho bảng Supplier
INSERT INTO Supplier (address,supplierName)
VALUES
('123 Main St, New York, NY', 'Global Sports Inc.'),
('456 Elm St, San Francisco, CA', 'Elite Shoes LLC'),
('789 Maple Ave, Chicago, IL', 'Active Wear Co.'),
('321 Oak St, Seattle, WA', 'Fit and Style Ltd.'),
('654 Pine St, Boston, MA', 'Performance Gear Corp.'),
('987 Cedar Rd, Austin, TX', 'Trendsetters Supply'),
('159 Spruce St, Miami, FL', 'Urban Outfits Inc.'),
('753 Birch St, Denver, CO', 'Trail Blazers Ltd.'),
('951 Aspen Dr, Atlanta, GA','Southern Comfort Gear'),
('852 Redwood Ave, Portland, OR','Pacific Edge Shoes');
INSERT INTO Supplier_PhoneNumber (supplierID, phoneNumbers) 
VALUES 
    (1, '0911111111'), 
    (1, '0911222222'), 
    (2, '0913333333'), 
    (3, '0914444444'), 
    (4, '0915555555'),
    (5, '0916666666'),
	(5, '0911111156'), 
    (6, '0911222999'), 
    (6, '0913333458'), 
    (7, '0914444454'), 
    (8, '0915554564'),
    (9, '0916666846');
INSERT INTO Product (description, price, productName, status, brandID, categoryID, supplierID, createDate)
VALUES
('High-performance running shoes.', 900000.00, 'Nike Air Max 270', 'Available', 1, 1,  1,GETDATE()),
('Lightweight basketball sneakers.', 1200000.00, 'Adidas Harden Vol. 5', 'Available', 2, 2, 2,GETDATE()),
('Stylish hiking boots.', 150000.00, 'Puma Timberland', 'Available', 3, 3,  3,GETDATE()),
('Everyday wear sneakers.', 800000.00, 'Reebok Chuck Taylor', 'Available', 4, 4,  4,GETDATE()),
('Elegant leather loafers.', 1000000.00, 'Vans Originals', 'Available', 5, 5, 5,GETDATE()),
('Performance tennis shoes.', 1100000.00, 'Nike Gel-Resolution', 'Available', 1, 6,  6,GETDATE()),
('Classic skate shoes.', 750000.00, 'Adidas Old Skool', 'Available', 2, 7,  7,GETDATE()),
('Premium long-distance runners.', 950000.00, 'Puma Ghost 14', 'Available', 3, 8, 8,GETDATE()),
('Breathable sports sandals.', 500000.00, 'Reebok Hurricane XLT2', 'Available', 4, 9, 9,GETDATE()),
('Luxury high heels.', 200000.00, 'Vans Choo Romy 100', 'Available', 5, 10,10,GETDATE()),
('Comfort-focused sneakers.', 850000.00, 'Nike Balance 574', 'Available', 1, 1, 1,GETDATE()),
('High-performance trainers.', 120000.00, 'Adidas Armour HOVR', 'Available', 2, 2,2,GETDATE()),
('Waterproof outdoor boots.', 130000.00, 'Puma Bugaboot', 'Available', 3, 3,3,GETDATE()),
('Athletic running shoes.', 100000.00, 'Reebok Endorphin', 'Available', 4, 4,4,GETDATE()),
('Modern casual loafers.', 900000.00, 'Vans Top-Sider', 'Available', 5, 5,5,GETDATE()),
('Lightweight running shoes.', 750000.00, 'Nike Reebok Floatride', 'Available', 1, 6, 6,GETDATE()),
('Classic canvas shoes.', 550000.00, 'Adidas Champion', 'Available', 2, 7, 7,GETDATE()),
('Trail running shoes.', 1100000.00, 'Puma Salomon Speedcross', 'Available', 3, 8, 8,GETDATE()),
('Minimalist sports sandals.', 600000.00, 'Reebok Xero Shoes Z-Trail', 'Available', 4, 9,9,GETDATE()),
('Luxury dress shoes.', 2500000.00, 'Vans Ace Sneakers', 'Available', 5, 10, 10,GETDATE()),
('Casual slip-on sneakers.', 700000.00, 'Adidas Go Walk', 'Available', 1, 1, 1,GETDATE()),
('Basketball performance shoes.', 140000.00, 'Adidas Clyde All-Pro', 'Available', 2, 2,2,GETDATE()),
('Hiking boots with ankle support.', 1600000.00, 'Puma Face Vectiv', 'Available', 3, 3,  3,GETDATE()),
('Canvas skate shoes.', 750000.00, 'Reebok Shoes Trase', 'Available', 4, 4, 4,GETDATE()),
('Formal leather oxford shoes.', 130000.00, 'Vans Allen Edmonds Park Ave', 'Available', 5, 5, 5,GETDATE()),
('Cushioned tennis shoes.', 1150000.00, 'Nike Axilus 2 Energized', 'Available', 1, 6, 6,GETDATE()),
('Classic low-top sneakers.', 900000.00, 'Adidas Carnaby Evo', 'Available', 2, 7, 7,GETDATE()),
('Trail running shoes for grip.', 1250000.00, 'Puma Hoka Speedgoat', 'Available', 3, 8, 8,GETDATE()),
('Open-toe sports sandals.', 450000.00, 'Reebok Hydro MOC', 'Available', 4, 9, 9,GETDATE()),
('Elegant party heels.', 220000.00, 'Vans Manolo Blahnik BB', 'Available', 5, 10, 10,GETDATE()),
('Everyday cushioned sneakers.', 1000000.00, 'Nike On Cloud 5', 'Available', 1, 1, 1,GETDATE()),
('Durable basketball sneakers.', 1500000.00, 'Adidas Jordan Retro 1', 'Available', 2, 2,2,GETDATE()),
('Warm insulated boots.', 180000.00, 'Puma Sorel Caribou', 'Available', 3, 3, 3,GETDATE()),
('Stylish slip-ons.', 700000.00, 'Reebok Toms Alpargata', 'Available', 4, 4,4,GETDATE()),
('Premium leather loafers.', 1900000.00, 'Vans Tods Gommino', 'Available', 5, 5,5,GETDATE()),
('High-traction trail runners.', 1300000.00, 'Nike Arcteryx Norvan', 'Available', 1, 6,6,GETDATE()),
('Skateboarding classics.', 750000.00, 'Adidas Globe Sabre', 'Available', 2, 7, 7,GETDATE()),
('All-terrain shoes.', 140000.00, 'Puma Lone Peak', 'Available', 3, 8, 8,GETDATE()),
('Quick-dry sandals.', 500000.00, 'Reebok Chaco Z/Cloud', 'Available', 4, 9, 9,GETDATE()),
('Luxury red sole heels.', 800000.00, 'Vans Christian Louboutin So Kate', 'Available', 5, 10,10,GETDATE()),
('Lightweight walking shoes.', 600000.00, 'Nike Skechers Flex Appeal', 'Available', 1, 1, 1,GETDATE()),
('Responsive basketball shoes.', 1300000.00, 'Adidas Zoom Freak', 'Available', 2, 2, 2,GETDATE()),
('Rugged hiking boots.', 1700000.00, 'Puma Zodiac Plus', 'Available', 3, 3,3,GETDATE()),
('Casual street sneakers.', 950000.00, 'Reebok Superstar', 'Available', 4, 4,4,GETDATE()),
('Italian leather dress shoes.', 2500000.00, 'Vans Double Monk', 'Available', 5, 5,5,GETDATE()),
('Responsive basketball shoes.', 1300000.00, 'Adidas Zoom Freak', 'Available', 2, 2, 2,GETDATE()),
('Rugged hiking boots.', 1700000.00, 'Puma Zodiac Plus', 'Available', 3, 3,3,GETDATE()),
('Casual street sneakers.', 950000.00, 'Reebok Superstar', 'Available', 4, 4, 4,GETDATE()),
('Italian leather dress shoes.', 2500000.00, 'Vans Double Monk', 'Available', 5, 5, 5,GETDATE());
--EXEC sp_help Product;
--ALTER TABLE Product
--ALTER COLUMN price DECIMAL(12, 2);
--DELETE FROM Product;
--UPDATE Product
--SET price = price * 10000;
INSERT INTO ProductDetail (color, size, stockQuantity, productID)
VALUES
('RED', 'SIZE_38', 50, 1),
('BLUE', 'SIZE_39', 30, 2),
('GREEN', 'SIZE_40', 20, 3),
('YELLOW', 'SIZE_41', 40, 4),
('BLACK', 'SIZE_42', 35, 5),
('WHITE', 'SIZE_43', 25, 6),
('PINK', 'SIZE_44', 60, 7),
('RED', 'SIZE_38', 45, 8),
('BLUE', 'SIZE_39', 50, 9),
('GREEN', 'SIZE_40', 20, 10),
('YELLOW', 'SIZE_41', 30, 11),
('BLACK', 'SIZE_42', 50, 12),
('WHITE', 'SIZE_43', 20, 13),
('PINK', 'SIZE_44', 40, 14),
('RED', 'SIZE_38', 35, 15),
('BLUE', 'SIZE_39', 25, 16),
('GREEN', 'SIZE_40', 30, 17),
('YELLOW', 'SIZE_41', 50, 18),
('BLACK', 'SIZE_42', 20, 19),
('WHITE', 'SIZE_43', 60, 20),
('PINK', 'SIZE_44', 40, 21),
('RED', 'SIZE_38', 50, 22),
('BLUE', 'SIZE_39', 45, 23),
('GREEN', 'SIZE_40', 30, 24),
('YELLOW', 'SIZE_41', 55, 25),
('BLACK', 'SIZE_42', 65, 26),
('WHITE', 'SIZE_43', 35, 27),
('PINK', 'SIZE_44', 45, 28),
('RED', 'SIZE_38', 40, 29),
('BLUE', 'SIZE_39', 25, 30),
('GREEN', 'SIZE_40', 50, 31),
('YELLOW', 'SIZE_41', 45, 32),
('BLACK', 'SIZE_42', 30, 33),
('WHITE', 'SIZE_43', 50, 34),
('PINK', 'SIZE_44', 40, 35),
('RED', 'SIZE_38', 35, 36),
('BLUE', 'SIZE_39', 30, 37),
('GREEN', 'SIZE_40', 50, 38),
('YELLOW', 'SIZE_41', 45, 39),
('BLACK', 'SIZE_42', 20, 40),
('WHITE', 'SIZE_43', 55, 41),
('PINK', 'SIZE_44', 60, 42),
('RED', 'SIZE_38', 25, 43),
('BLUE', 'SIZE_39', 40, 44),
('GREEN', 'SIZE_40', 45, 45),
('YELLOW', 'SIZE_41', 30, 46),
('BLACK', 'SIZE_42', 20, 47),
('WHITE', 'SIZE_43', 35, 48),
('PINK', 'SIZE_44', 50, 49),
('RED', 'SIZE_38', 60, 50);

INSERT INTO Product_ImageURL (productID, imageURL)
VALUES
(1, 'product1.png'),
(2, 'product2.png'),
(3, 'product3.png'),
(4, 'product4.png'),
(5, 'product5.png'),
(6, 'product6.png'),
(7, 'product7.png'),
(8, 'product8.png'),
(9, 'product9.png'),
(10, 'product10.png'),
(11, 'product11.png'),
(12, 'product12.png'),
(13, 'product13.png'),
(14, 'product14.png'),
(15, 'product15.png'),
(16, 'product16.png'),
(17, 'product17.png'),
(18, 'product18.png'),
(19, 'product19.png'),
(20, 'product20.png'),
(21, 'product21.png'),
(22, 'product22.png'),
(23, 'product23.png'),
(24, 'product24.png'),
(25, 'product25.png'),
(26, 'product26.png'),
(27, 'product27.png'),
(28, 'product28.png'),
(29, 'product29.png'),
(30, 'product30.png'),
(31, 'product31.png'),
(32, 'product32.png'),
(33, 'product33.png'),
(34, 'product34.png'),
(35, 'product35.png'),
(36, 'product36.png'),
(37, 'product37.png'),
(38, 'product38.png'),
(39, 'product39.png'),
(40, 'product40.png'),
(41, 'product41.png'),
(42, 'product42.png'),
(43, 'product43.png'),
(44, 'product44.png'),
(45, 'product45.png'),
(46, 'product46.png'),
(47, 'product47.png'),
(48, 'product48.png'),
(49, 'product49.png'),
(50, 'product50.png');



INSERT INTO Review (contents, createDate, start, productID, userID)
VALUES
('Great quality!', '2025-03-01', 5, 1, 4),
('Comfortable to wear.', '2025-03-02', 4, 2, 5),
('Stylish and durable.', '2025-03-03', 5, 3, 4),
('Affordable price.', '2025-03-04', 4, 4, 6),
('Highly recommend!', '2025-03-05', 5, 5, 13),
('Not true to size.', '2025-03-06', 3, 6, 14),
('Great for sports.', '2025-03-07', 5, 7, 7),
('Very comfortable.', '2025-03-08', 4, 8, 8),
('Looks amazing.', '2025-03-09', 5, 9, 9),
('Best shoes ever!', '2025-03-10', 5, 10, 10),
('Fast shipping.', '2025-03-11', 4, 11, 11),
('Perfect fit.', '2025-03-12', 5, 12, 12),
('Good value.', '2025-03-13', 4, 13, 13),
('Highly functional.', '2025-03-14', 5, 14, 10),
('Not very durable.', '2025-03-15', 3, 15, 9),
('Looks premium.', '2025-03-16', 4, 16, 8),
('Works as expected.', '2025-03-17', 5, 17, 7),
('Could be better.', '2025-03-18', 3, 18, 5),
('Excellent material.', '2025-03-19', 5, 19, 12),
('Highly recommend!', '2025-03-20', 5, 20, 14);


--ShoeStore_UserService
use [ShoeStore_UserService]
INSERT INTO Role (name, description)
VALUES
('Admin', 'Quản trị viên với toàn quyền quản lý'),
('Customer', 'Khách hàng sử dụng dịch vụ và mua sản phẩm')

INSERT INTO Users (CI, email, name, password, phoneNumber, status, userName, roleID)
VALUES
-- Admins
('1234567890', 'admin1@example.com', 'Admin User 1', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '1234567890', 'Active', 'admin1', 1),
('0987654321', 'admin2@example.com', 'Admin User 2', 'securepassword2', '0987654321', 'Active', 'admin2', 1),
('1122334455', 'admin3@example.com', 'Admin User 3', 'securepassword3', '1122334455', 'Active', 'admin3', 1),
-- Customers
('02233445566', 'customer1@example.com', 'John Doe', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '2233445566', 'Active', 'johndoe1', 2),
('03344556677', 'hdthieu2003@gmail.com', 'Jane Smith', 'password456', '3344556677', 'Active', 'janesmith2', 2),
('04455667788', 'customer3@example.com', 'Alice Johnson', 'password789', '4455667788', 'Active', 'alicejohnson3', 2),
('05566778899', 'customer4@example.com', 'Bob Brown', 'password101', '5566778899', 'Active', 'bobbrown4', 2),
('06677889900', 'customer5@example.com', 'Charlie Davis', 'password202', '6677889900', 'Active', 'charliedavis5', 2),
('07788990011', 'customer6@example.com', 'Diana Prince', 'password303', '7788990011', 'Active', 'dianaprince6', 2),
('08899001122', 'customer7@example.com', 'Ethan Hunt', 'password404', '8899001122', 'Active', 'ethanhunt7', 2),
('01819202122', 'customer17@example.com', 'Oscar Isaac', 'password444', '1819202122', 'Active', 'oscarisaac17', 2),
('01920212223', 'customer18@example.com', 'Peter Parker', 'password555', '1920212223', 'Active', 'peterparker18', 2),
('02021222324', 'customer19@example.com', 'Quinn Fabray', 'password666', '2021222324', 'Active', 'quinnfabray19', 2),
('02122232425', 'customer20@example.com', 'Rachel Green', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '2122232425', 'Active', 'rachelgreen20', 2);


INSERT INTO Address (city, district, street, ward, userID)
VALUES 
('Hanoi', 'Ba Dinh', '123 Le Loi', 'Ward 1', 1),
('Hanoi', 'Cau Giay', '456 Hoang Hoa Tham', 'Ward 2', 2),
('Hanoi', 'Dong Da', '789 Nguyen Chi Thanh', 'Ward 3', 3),
('Ho Chi Minh', 'District 1', '12 Hai Ba Trung', 'Ward 4', 4),
('Ho Chi Minh', 'District 2', '34 Nguyen Hue', 'Ward 5', 5),
('Da Nang', 'Hai Chau', '56 Tran Phu', 'Ward 6', 6),
('Da Nang', 'Thanh Khe', '78 Dien Bien Phu', 'Ward 7', 7),
('Can Tho', 'Ninh Kieu', '90 Vo Van Kiet', 'Ward 8', 8),
('Hai Phong', 'Le Chan', '11 Nguyen Trai', 'Ward 9', 9),
('Hai Phong', 'Ngo Quyen', '22 Hai Ba Trung', 'Ward 10', 10),
('Hue', 'Phu Nhuan', '33 Nguyen Cong Tru', 'Ward 11', 11),
('Hue', 'Thuan Thanh', '44 Le Loi', 'Ward 12', 12),
('Nha Trang', 'Van Thanh', '55 Tran Phu', 'Ward 13', 13),
('Nha Trang', 'Phuoc Long', '66 Nguyen Thien Thuat', 'Ward 14', 14)

INSERT INTO Cart (createAt, userID)
VALUES 
('2025-03-01 08:30:00', 1),
('2025-03-02 09:45:00', 2),
('2025-03-03 10:15:00', 3),
('2025-03-04 11:20:00', 4),
('2025-03-05 12:30:00', 5),
('2025-03-06 13:45:00', 6),
('2025-03-07 14:50:00', 7),
('2025-03-08 15:10:00', 8),
('2025-03-09 16:30:00', 9),
('2025-03-10 17:00:00', 10),
('2025-03-11 18:15:00', 11),
('2025-03-12 19:20:00', 12),
('2025-03-13 20:25:00', 13),
('2025-03-14 21:30:00', 14)


INSERT INTO CartItem (quantity, subTotal, cartID, productDetailID)
VALUES
(2, 180.00, 1, 1), -- Nike Air Max 270, $90.00 * 2
(1, 120.00, 2, 2), -- Adidas Harden Vol. 5, $120.00 * 1
(3, 450.00, 3, 3), -- Timberland Premium, $150.00 * 3
(4, 320.00, 4, 4), -- Converse Chuck Taylor, $80.00 * 4
(2, 200.00, 5, 5), -- Clarks Originals, $100.00 * 2
(1, 110.00, 6, 6), -- Asics Gel-Resolution, $110.00 * 1
(5, 375.00, 7, 7), -- Vans Old Skool, $75.00 * 5
(3, 285.00, 8, 8), -- Brooks Ghost 14, $95.00 * 3
(1, 50.00, 9, 9), -- Teva Hurricane XLT2, $50.00 * 1
(2, 400.00, 10, 10), -- Jimmy Choo Romy 100, $200.00 * 2
(4, 340.00, 11, 11), -- New Balance 574, $85.00 * 4
(1, 120.00, 12, 12), -- Under Armour HOVR, $120.00 * 1
(2, 260.00, 13, 13), -- Columbia Bugaboot, $130.00 * 2
(5, 500.00, 14, 14), -- Saucony Endorphin, $100.00 * 5
(2, 140.00, 1, 21), -- Skechers Go Walk, $70.00 * 2
(3, 420.00, 2, 22), -- Puma Clyde All-Pro, $140.00 * 3
(5, 800.00, 3, 23), -- North Face Vectiv, $160.00 * 5
(1, 75.00, 4, 24), -- DC Shoes Trase, $75.00 * 1
(2, 260.00, 5, 25), -- Allen Edmonds Park Ave, $130.00 * 2
(4, 460.00, 6, 26), -- Fila Axilus 2 Energized, $115.00 * 4
(1, 90.00, 7, 27), -- Lacoste Carnaby Evo, $90.00 * 1
(3, 375.00, 8, 28), -- Hoka Speedgoat, $125.00 * 3
(2, 90.00, 9, 29), -- Merrell Hydro MOC, $45.00 * 2
(1, 220.00, 10, 30), -- Manolo Blahnik BB, $220.00 * 1
(2, 200.00, 11, 31), -- On Cloud 5, $100.00 * 2
(5, 750.00, 12, 32), -- Jordan Retro 1, $150.00 * 5
(1, 180.00, 13, 33), -- Sorel Caribou, $180.00 * 1
(3, 210.00, 14, 34), -- Toms Alpargata, $70.00 * 3
(1, 60.00, 1, 41), -- Skechers Flex Appeal, $60.00 * 1
(3, 390.00, 2, 42), -- Nike Zoom Freak, $130.00 * 3
(5, 850.00, 3, 43), -- Scarpa Zodiac Plus, $170.00 * 5
(2, 190.00, 4, 44), -- Adidas Superstar, $95.00 * 2
(4, 1000.00, 5, 45), -- Santoni Double Monk, $250.00 * 4
(1, 90.00, 6, 46), -- Nike Air Max 270, $90.00 * 1
(3, 330.00, 7, 47), -- Adidas Harden Vol. 5, $110.00 * 3
(2, 300.00, 8, 48), -- Timberland Premium, $150.00 * 2
(4, 640.00, 9, 49), -- Converse Chuck Taylor, $80.00 * 4
(5, 500.00, 10, 50); -- Clarks Originals, $100.00 * 5

--ShoeStore_PaymentService
use ShoeStore_PaymentService

INSERT INTO Payment (paymentDate, status, orderID)
VALUES
('2025-03-01', 'Completed', 1),
('2025-03-02', 'Pending', 2),
('2025-03-03', 'Failed', 3),
('2025-03-04', 'Completed', 4),
('2025-03-05', 'Completed', 5),
('2025-03-06', 'Pending', 6),
('2025-03-07', 'Completed', 7),
('2025-03-08', 'Pending', 8),
('2025-03-09', 'Failed', 9),
('2025-03-10', 'Completed', 10),
('2025-03-11', 'Pending', 11),
('2025-03-12', 'Completed', 12),
('2025-03-13', 'Completed', 13),
('2025-03-14', 'Pending', 14),
('2025-03-15', 'Failed', 15),
('2025-03-16', 'Completed', 16),
('2025-03-17', 'Pending', 17),
('2025-03-18', 'Completed', 18),
('2025-03-19', 'Failed', 19),
('2025-03-20', 'Completed', 20),
('2025-03-21', 'Pending', 21),
('2025-03-22', 'Completed', 22),
('2025-03-23', 'Failed', 23),
('2025-03-24', 'Completed', 24),
('2025-03-25', 'Completed', 25),
('2025-03-26', 'Pending', 26),
('2025-03-27', 'Completed', 27),
('2025-03-28', 'Completed', 28),
('2025-03-29', 'Pending', 29),
('2025-03-30', 'Failed', 30);


INSERT INTO Receipt (receiptDate, total, paymentID)
VALUES
('2025-03-01', 180.00, 1),
('2025-03-02', 120.00, 2),
('2025-03-03', 450.00, 3),
('2025-03-04', 320.00, 4),
('2025-03-05', 200.00, 5),
('2025-03-06', 110.00, 6),
('2025-03-07', 375.00, 7),
('2025-03-08', 285.00, 8),
('2025-03-09', 50.00, 9),
('2025-03-10', 400.00, 10),
('2025-03-11', 340.00, 11),
('2025-03-12', 120.00, 12),
('2025-03-13', 260.00, 13),
('2025-03-14', 500.00, 14),
('2025-03-15', 270.00, 15),
('2025-03-16', 75.00, 16),
('2025-03-17', 110.00, 17),
('2025-03-18', 330.00, 18),
('2025-03-19', 60.00, 19),
('2025-03-20', 1000.00, 20),
('2025-03-21', 140.00, 21),
('2025-03-22', 420.00, 22),
('2025-03-23', 800.00, 23),
('2025-03-24', 320.00, 24),
('2025-03-25', 260.00, 25),
('2025-03-26', 460.00, 26),
('2025-03-27', 390.00, 27),
('2025-03-28', 300.00, 28),
('2025-03-29', 250.00, 29),
('2025-03-30', 520.00, 30);