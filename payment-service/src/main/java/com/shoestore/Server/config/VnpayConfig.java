package com.shoestore.Server.config;

public class VnpayConfig {
    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String vnp_TmnCode = "4OZL59CV"; // mã TMN từ VNPay
    public static final String vnp_HashSecret = " M9J5JIQRA4IGIHHVIHNX0SCS7GBFNZ3D"; // key bí mật do VNPay cấp
    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_ReturnUrl = "http://localhost:8765/Order/vnpay_return"; // hoặc URL FE nếu bạn muốn redirect về giao diện FE
}