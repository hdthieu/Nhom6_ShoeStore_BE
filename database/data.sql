--ShoeStore_ProductService
use ShoeStore_ProductService

INSERT INTO Voucher (description, discountType, discountValue, endDate, name, startDate, status, minValueOrder)
VALUES
('Welcome Voucher', 'Percentage', 10, '2024-12-31', 'WELCOME10', '2024-01-01', 'Upcoming', 35000),
('Holiday Discount', 'Flat', 20, '2024-12-25', 'HOLIDAY20', '2024-12-01', 'Active', 25000),
('Year-End Sale', 'Percentage', 15, '2024-12-31', 'YEAR15', '2024-12-15', 'Ended', 25000);

--ShoeStore_OrderService
use ShoeStore_OrderService

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