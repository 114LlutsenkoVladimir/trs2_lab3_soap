package org.example.trs2_lab.soap.endPoint.product;

import org.example.trs2_lab.entity.Product;
import org.example.trs2_lab.repository.base.ProductRepository;
import org.example.trs2_lab.soap.GetProductsByCategoryIdRequest;
import org.example.trs2_lab.soap.GetProductsByCategoryIdResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import
import java.util.List;

@Endpoint
public class ProductSoapEndpoint {
    private static final String NAMESPACE_URI = "http://mycompany.com/products";

    private final ProductRepository productRepository;
    // Внедряем ваш существующий репозиторий через конструктор
    public ProductSoapEndpoint(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductsByManufacturerRequest")
    @ResponsePayload
    public GetProductsByCategoryIdResponse getProducts(@RequestPayload GetProductsByCategoryIdRequest request) {

        // Используем ваш обычный метод репозитория!
        List<Product> entities = productRepository.findByManufacturerId(request.getManufacturerId());

        // Превращаем Entity из базы в XML-объекты для ответа
        GetProductsByManufacturerResponse response = new GetProductsByManufacturerResponse();
        for (Product entity : entities) {
            ProductSoapModel s = new ProductSoapModel();
            s.setId(entity.getId());
            s.setName(entity.getName());
            response.getProducts().add(s);
        }

        return response;
    }
}
