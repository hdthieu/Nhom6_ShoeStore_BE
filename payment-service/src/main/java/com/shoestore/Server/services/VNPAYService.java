package com.shoestore.Server.services;

import com.shoestore.Server.config.VNPAYConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VNPAYService {

    public String createOrder(HttpServletRequest request, int amount, String orderInfo, String baseUrl) {
        String vnp_TxnRef = getRandomNumber(8);
        String vnp_IpAddr = getIpAddress(request);
        String vnp_CreateDate = getCurrentTime();
        String vnp_ExpireDate = getExpireTime();

        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNPAYConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8765/payment/vnpay_return");

        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        String hashData = hashAllFields(vnp_Params);
        String vnp_SecureHash = hmacSHA512(VNPAYConfig.vnp_HashSecret, hashData);
        String queryUrl = buildQueryUrl(vnp_Params) + "&vnp_SecureHash=" + vnp_SecureHash;

        return VNPAYConfig.vnp_PayUrl + "?" + queryUrl;
    }

    public int verifyReturn(Map<String, String> paramMap) {
        // ⚠️ FIX lỗi immutable map
        Map<String, String> params = new HashMap<>(paramMap);

        String receivedHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        String hashData = hashAllFields(params);
        String expectedHash = hmacSHA512(VNPAYConfig.vnp_HashSecret, hashData);

        if (receivedHash != null && receivedHash.equalsIgnoreCase(expectedHash)) {
            return "00".equals(params.get("vnp_ResponseCode")) ? 1 : 0;
        }

        return -1;
    }


    private String buildQueryUrl(Map<String, String> params) {
        return params.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.US_ASCII) + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT+7"));
        return sdf.format(new Date());
    }

    private String getExpireTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        calendar.add(Calendar.MINUTE, 15);
        return new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        return ip == null ? request.getRemoteAddr() : ip;
    }

    private String getRandomNumber(int len) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }
        return sb.toString();
    }

    private String hashAllFields(Map<String, String> fields) {
        return fields.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.US_ASCII) + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));
    }


    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
