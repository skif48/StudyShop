package com.shop.service;

import com.shop.entity.Attribute;
import com.shop.entity.AttributeValue;
import com.shop.entity.Product;
import com.shop.entity.ProductType;
import com.shop.error.ErrorCode;
import com.shop.error.ServiceException;
import com.shop.repository.AttributeRepository;
import com.shop.repository.AttributeValueRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@Service
public class ShopService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public ShopService() {
    }

    public ServiceResponse putProduct(Product product, String type) {
        ServiceResponse response = new ServiceResponse();
        try {
            ProductType productType = productTypeRepository.findByTypeName(type);
            product.setType(productType);
            productRepository.save(product);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc){
            exc.printStackTrace();
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse getProduct(UUID uuid) {
        ServiceResponse response = new ServiceResponse();
        try {
            Product product = productRepository.findByUuid(uuid.toString());
            if(product == null){
                response.setException(new ServiceException(ErrorCode.NO_SUCH_PRODUCT, "no product with following uuid is found in database: " + uuid.toString(), null));
                response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
                return response;
            }
            response.setData(product);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc) {
            exc.printStackTrace();
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse getProductFullInfo(UUID uuid){
        ServiceResponse response = new ServiceResponse();
        try{
            List<Object[]> info = productRepository.getFullInfoByUuid(uuid.toString());
            if(info == null || info.isEmpty()){
                String message = "something went wrong with obtaining attributes of product with the following UUID: " + uuid.toString() + "; ";
                response.setException(new ServiceException(ErrorCode.NO_ATTRIBUTES_FOR_PRODUCT, message, null));
                response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
                return response;
            }
            ProductInfo productInfo = new ProductInfo();
            productInfo.manageAttributes(info);
            Product product = productRepository.findByUuid(uuid.toString());
            if(product == null){
                String message = "no product found with the following UUID: " + uuid.toString();
                response.setException(new ServiceException(ErrorCode.NO_SUCH_PRODUCT, message, null));
                response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
                return response;
            }
            productInfo.setProduct(product);
            response.setData(productInfo);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc){
            exc.printStackTrace();
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse getAllProducts() {
        ServiceResponse response = new ServiceResponse();
        try {
            Iterable<Product> products = productRepository.findAll();
            response.setData(products);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc) {
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse deleteProductByUUID(UUID uuid) {
        ServiceResponse response = new ServiceResponse();
        try{
            productRepository.deleteByUuid(uuid.toString());
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc){
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse deleteAllProducts(){
        ServiceResponse response = new ServiceResponse();
        try{
            productRepository.deleteAll();
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc){
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse addAttribute(Attribute attribute){
        ServiceResponse response = new ServiceResponse();
        try{
            attributeRepository.save(attribute);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc){
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse addAttributeValue(AttributeValue attributeValue, UUID uuid, String attributeName){
        ServiceResponse response = new ServiceResponse();
        try{
            attributeValue = setUpAttributeValue(attributeValue, uuid, attributeName);
            attributeValueRepository.save(attributeValue);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc){
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }

    }

    private AttributeValue setUpAttributeValue(AttributeValue attributeValue, UUID uuid, String attributeName){
        Product product = productRepository.findByUuid(uuid.toString());
        attributeValue.setProduct(product);
        Attribute attribute = attributeRepository.findByName(attributeName);
        attributeValue.setAttribute(attribute);

        return attributeValue;
    }

    public ServiceResponse getProductsOfType(ProductType type){
        ServiceResponse response = new ServiceResponse();
        try {
            List<Product> products = productRepository.findByType(type);
            response.setData(products);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc) {
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }

    public ServiceResponse addType(ProductType type){
        ServiceResponse response = new ServiceResponse();
        try {
            productTypeRepository.save(type);
            response.setStatus(ServiceResponse.ServiceStatus.SUCCESS);
            return response;
        } catch (Exception exc) {
            response.setStatus(ServiceResponse.ServiceStatus.FAILURE);
            response.setException(exc);
            return response;
        }
    }
}