package org.example.trs2_lab.soap.endPoint.product;

import org.example.trs2_lab.dto.ProductManufacturerCategory;
import org.example.trs2_lab.entity.Product;
import org.example.trs2_lab.repository.base.ProductRepository;
import org.example.trs2_lab.service.ProductService;
import org.example.trs2_lab.soap.GetProductsByCategoryIdRequest;
import org.example.trs2_lab.soap.GetProductsByCategoryIdResponse;
import org.example.trs2_lab.soap.ProductType;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ProductSoapEndpoint {
    private static final String NAMESPACE_URI = "http://mycompany.com/products";

    private final ProductService service;


    public ProductSoapEndpoint(ProductService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductsByManufacturerRequest")
    @ResponsePayload
    public GetProductsByCategoryIdResponse getProducts(@RequestPayload GetProductsByCategoryIdRequest request) {


        List<ProductManufacturerCategory> dtos = service.findByCategoryIdDto(request.getCategoryId());


        GetProductsByCategoryIdResponse response = new GetProductsByCategoryIdResponse();
        for (ProductManufacturerCategory dto : dtos) {
            ProductType s = new ProductType();
            s.setId(dto.getId());
            s.setName(dto.getName());
            s.setPrice(dto.getPrice());
            s.setCategoryName(dto.getCategoryName());
            s.setManufacturerName(dto.getManufacturerName());
            response.getProduct().add(s);
        }

        return response;
    }
}
