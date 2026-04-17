package org.example.trs2_lab.soap.client;

import org.example.trs2_lab.soap.GetProductsByCategoryIdRequest;
import org.example.trs2_lab.soap.GetProductsByCategoryIdResponse;
import org.example.trs2_lab.soap.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

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
}
