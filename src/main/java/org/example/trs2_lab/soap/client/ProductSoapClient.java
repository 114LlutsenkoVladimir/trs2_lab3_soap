package org.example.trs2_lab.soap.client;

import org.example.trs2_lab.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductSoapClient {

    @Autowired
    private WebServiceTemplate webServiceTemplate;


    public List<ProductType> getProductsByCategoryId(Long categoryId) {
        // Створюємо згенерований клас запиту
        GetProductsByCategoryIdRequest request = new GetProductsByCategoryIdRequest();
        request.setCategoryId(categoryId);

        // Відправляємо запит і отримуємо відповідь
        // Spring сам перетворить request в XML, а відповідь XML в об'єкт Response
        GetProductsByCategoryIdResponse response = (GetProductsByCategoryIdResponse)
                webServiceTemplate.marshalSendAndReceive(request);

        return response.getProduct();
    }

    public List<ProductType> getProductsByManufacturerId(Long manufacturerId) {

        GetProductsByManufacturerIdRequest request = new GetProductsByManufacturerIdRequest();
        request.setManufacturerId(manufacturerId);

        GetProductsByManufacturerIdResponse response = (GetProductsByManufacturerIdResponse)
                webServiceTemplate.marshalSendAndReceive(request);

        return response.getProduct();
    }

    public List<ProductType> getProductsByPriceBetween(BigDecimal from, BigDecimal to) {

        GetProductsByPriceBetweenRequest request = new GetProductsByPriceBetweenRequest();
        request.setFrom(from);
        request.setTo(to);

        GetProductsByPriceBetweenResponse response = (GetProductsByPriceBetweenResponse)
                webServiceTemplate.marshalSendAndReceive(request);

        return response.getProduct();
    }
}
