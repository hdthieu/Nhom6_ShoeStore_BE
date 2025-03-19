--ShoeStore_ProductService
use ShoeStore_ProductService

INSERT INTO Voucher (description, discountType, discountValue, endDate, name, startDate, status, minValueOrder)
VALUES
('Welcome Voucher', 'Percentage', 10, '2024-12-31', 'WELCOME10', '2024-01-01', 'Upcoming', 35000),
('Holiday Discount', 'Flat', 20, '2024-12-25', 'HOLIDAY20', '2024-12-01', 'Active', 25000),
('Year-End Sale', 'Percentage', 15, '2024-12-31', 'YEAR15', '2024-12-15', 'Ended', 25000);

--ShoeStore_OrderService
use ShoeStore_OrderService

-- Thêm dữ liệu vào bảng Users
INSERT INTO Users (CI, email, name, password, phoneNumber, status, userName, roleID)
VALUES
-- Admins
('1234567890', 'admin1@example.com', 'Admin User 1', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0334567890', 'Active', 'admin1', 1),
('0987654321', 'admin2@example.com', 'Admin User 2', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0987654321', 'Active', 'admin2', 1),
('1122334455', 'admin3@example.com', 'Admin User 3', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0322334455', 'Active', 'admin3', 1),

-- Customers
('2233445566', 'customer1@example.com', 'John Doe', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0333445566', 'Active', 'johndoe1', 2),
('3344556677', 'customer2@example.com', 'Jane Smith', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0944556677', 'Active', 'janesmith2', 2),
('4455667788', 'customer3@example.com', 'Alice Johnson', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0355667788', 'Active', 'alicejohnson3', 2),
('5566778899', 'customer4@example.com', 'Bob Brown', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0366778899', 'Active', 'bobbrown4', 2),
('6677889900', 'customer5@example.com', 'Charlie Davis', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0377889900', 'Active', 'charliedavis5', 2),
('7788990011', 'customer6@example.com', 'Diana Prince', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0388990011', 'Active', 'dianaprince6', 2),
('8899001122', 'customer7@example.com', 'Ethan Hunt', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0399001122', 'Active', 'ethanhunt7', 2),
('9900112233', 'customer8@example.com', 'Fiona Gallagher', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0300112233', 'Active', 'fionagallagher8', 2),
('1011121314', 'customer9@example.com', 'George Clooney', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0311121314', 'Active', 'georgeclooney9', 2),
('1112131415', 'customer10@example.com', 'Hannah Montana', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0312131415', 'Active', 'hannahmontana10', 2),
('1213141516', 'customer11@example.com', 'Isaac Newton', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0313141516', 'Active', 'isaacnewton11', 2),
('1314151617', 'customer12@example.com', 'Jack Sparrow', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0314151617', 'Active', 'jacksparrow12', 2),
('1415161718', 'customer13@example.com', 'Kara Danvers', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0315161718', 'Active', 'karadanvers13', 2),
('1516171819', 'customer14@example.com', 'Liam Hemsworth', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0316171819', 'Active', 'liamhemsworth14', 2),
('1617181920', 'customer15@example.com', 'Mia Wallace', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0317181920', 'Active', 'miawallace15', 2),
('1718192021', 'customer16@example.com', 'Nina Dobrev', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0318192021', 'Active', 'ninadobrev16', 2),
('1819202122', 'customer17@example.com', 'Oscar Isaac', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0319202122', 'Active', 'oscarisaac17', 2),
('1920212223', 'customer18@example.com', 'Peter Parker', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0320212223', 'Active', 'peterparker18', 2),
('2021222324', 'customer19@example.com', 'Quinn Fabray', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '0321222324', 'Active', 'quinnfabray19', 2),
('2122232425', 'customer20@example.com', 'Rachel Green', '$2a$10$piYOHuFhF7WWTyziAev08.RtlRcnZuruhfrTrgYWO6phJ4l1XvSBm', '2122232425', 'Active', 'rachelgreen20', 2);


INSERT INTO Orders (feeShip, orderDate, shippingAddress, status,[userID] , [voucherID],[paymentID])
VALUES
    (5.00, '2024-11-01', '123 Le Loi, Hanoi', 'Shipped', 5, 1, 0),
    (10.00, '2024-11-02', '456 Hoang Hoa Tham, Hanoi', 'Processing', 4, 2, 0),
    (0.00, '2024-11-03', '789 Nguyen Chi Thanh, Hanoi', 'Delivered', 6, 3, 0),
    (12.00, '2024-11-04', '12 Hai Ba Trung, Ho Chi Minh', 'Shipped', 4, 1, 0),
    (15.00, '2024-11-05', '34 Nguyen Hue, Ho Chi Minh', 'Processing', 5, 1, 0),
    (8.00, '2024-11-06', '56 Tran Phu, Da Nang', 'Shipped', 6, 2, 0),
    (6.50, '2024-11-07', '78 Dien Bien Phu, Da Nang', 'Delivered', 7, 2, 0),
    (9.00, '2024-11-08', '90 Vo Van Kiet, Can Tho', 'Processing', 8, 2, 0),
    (5.00, '2024-11-09', '11 Nguyen Trai, Hai Phong', 'Shipped', 6, 0, 0),
    (10.00, '2024-11-10', '22 Hai Ba Trung, Hai Phong', 'Delivered', 10, 1, 0),
    (7.50, '2024-11-11', '33 Nguyen Cong Tru, Hue', 'Processing', 11, 0, 0),
    (12.00, '2024-11-12', '44 Le Loi, Hue', 'Shipped', 12, 2, 0),
    (15.00, '2024-11-13', '55 Tran Phu, Nha Trang', 'Processing', 13, 0, 0),
    (8.00, '2024-11-14', '66 Nguyen Thien Thuat, Nha Trang', 'Delivered', 14, 3, 0),
    (6.50, '2024-11-15', '77 Tran Hung Dao, Vung Tau', 'Processing', 15, 0, 0),
    (9.00, '2024-11-16', '88 Nguyen Trai, Vung Tau', 'Shipped', 16, 1, 0),
    (5.00, '2024-11-17', '99 Le Hong Phong, Da Lat', 'Delivered', 17, 0, 0),
    (10.00, '2024-11-18', '10 Nguyen Van Cu, Da Lat', 'Processing', 18, 0, 0),
    (7.50, '2024-11-19', '20 Tran Hung Dao, Quy Nhon', 'Shipped', 19, 1, 0),
    (12.00, '2024-11-20', '30 Vo Thi Sau, Quy Nhon', 'Delivered', 20, 1, 0),
    (15.00, '2024-11-21', '123 Le Loi, Hanoi', 'Shipped', 5, 2, 0),
    (8.00, '2024-11-22', '456 Hoang Hoa Tham, Hanoi', 'Processing', 10, 3, 0),
    (6.50, '2024-11-23', '789 Nguyen Chi Thanh, Hanoi', 'Delivered', 8, 0, 0),
    (9.00, '2024-11-24', '12 Hai Ba Trung, Ho Chi Minh', 'Processing', 4, 1, 0),
    (0.00, '2024-11-25', '34 Nguyen Hue, Ho Chi Minh', 'Delivered', 5, 2, 0),
    (0.00, '2024-11-26', '56 Tran Phu, Da Nang', 'Shipped', 6, 0, 0),
    (0.00, '2024-11-27', '78 Dien Bien Phu, Da Nang', 'Processing', 7, 3, 0),
    (0.00, '2024-11-28', '90 Vo Van Kiet, Can Tho', 'Delivered', 8, 1, 0),
    (0.00, '2024-11-29', '11 Nguyen Trai, Hai Phong', 'Shipped', 9, 1, 0),
    (0.00, '2024-11-30', '22 Hai Ba Trung, Hai Phong', 'Processing', 10, 0, 0);

INSERT INTO OrderDetail (price, quantity,[productDetail], orderID)
VALUES
(90.00, 2, 1, 1), -- Nike Air Max 270
(120.00, 1, 2, 2), -- Adidas Harden Vol. 5
(150.00, 3, 3, 3), -- Timberland Premium
(80.00, 4, 4, 4), -- Converse Chuck Taylor
(100.00, 2, 5, 5), -- Clarks Originals
(110.00, 1, 6, 6), -- Asics Gel-Resolution
(75.00, 5, 7, 7), -- Vans Old Skool
(95.00, 3, 8, 8), -- Brooks Ghost 14
(50.00, 1, 9, 9), -- Teva Hurricane XLT2
(200.00, 2, 10, 10), -- Jimmy Choo Romy 100
(85.00, 4, 11, 11), -- New Balance 574
(120.00, 1, 12, 12), -- Under Armour HOVR
(130.00, 2, 13, 13), -- Columbia Bugaboot
(100.00, 5, 14, 14), -- Saucony Endorphin
(90.00, 3, 15, 15), -- Sperry Top-Sider
(75.00, 1, 16, 16), -- Reebok Floatride
(55.00, 2, 17, 17), -- Keds Champion
(110.00, 3, 18, 18), -- Salomon Speedcross
(60.00, 1, 19, 19), -- Xero Shoes Z-Trail
(250.00, 4, 20, 20), -- Gucci Ace Sneakers
(70.00, 2, 21, 21), -- Skechers Go Walk
(140.00, 3, 22, 22), -- Puma Clyde All-Pro
(160.00, 5, 23, 23), -- North Face Vectiv
(75.00, 1, 24, 24), -- DC Shoes Trase
(130.00, 2, 25, 25), -- Allen Edmonds Park Ave
(115.00, 4, 26, 26), -- Fila Axilus 2 Energized
(90.00, 1, 27, 27), -- Lacoste Carnaby Evo
(125.00, 3, 28, 28), -- Hoka Speedgoat
(45.00, 2, 29, 29), -- Merrell Hydro MOC
(220.00, 1, 30, 30), -- Manolo Blahnik BB
(100.00, 2, 31, 1), -- On Cloud 5
(150.00, 5, 32, 2), -- Jordan Retro 1
(180.00, 1, 33, 3), -- Sorel Caribou
(70.00, 3, 34, 4), -- Toms Alpargata
(190.00, 4, 35, 5), -- Tods Gommino
(130.00, 2, 36, 6), -- Arcteryx Norvan
(75.00, 3, 37, 7), -- Globe Sabre
(140.00, 5, 38, 8), -- Altra Lone Peak
(50.00, 2, 39, 9), -- Chaco Z/Cloud
(800.00, 4, 40, 10), -- Christian Louboutin So Kate
(60.00, 1, 41, 11), -- Skechers Flex Appeal
(130.00, 3, 42, 12), -- Nike Zoom Freak
(170.00, 5, 43, 13), -- Scarpa Zodiac Plus
(95.00, 2, 44, 14), -- Adidas Superstar
(250.00, 4, 45, 15); -- Santoni Double Monk

