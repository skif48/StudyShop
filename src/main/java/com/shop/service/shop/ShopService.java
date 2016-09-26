package com.shop.service.shop;

import com.shop.domain.entity.*;
import com.shop.error.ErrorCode;
import com.shop.error.ServiceException;
import com.shop.repository.products.*;
import com.shop.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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

    @Autowired
    private ProductImageRepository productImageRepository;

    public ShopService() {
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public AttributeRepository getAttributeRepository() {
        return attributeRepository;
    }

    public void setAttributeRepository(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public AttributeValueRepository getAttributeValueRepository() {
        return attributeValueRepository;
    }

    public void setAttributeValueRepository(AttributeValueRepository attributeValueRepository) {
        this.attributeValueRepository = attributeValueRepository;
    }

    public ProductTypeRepository getProductTypeRepository() {
        return productTypeRepository;
    }

    public void setProductTypeRepository(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public ProductImageRepository getProductImageRepository() {
        return productImageRepository;
    }

    public void setProductImageRepository(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public void putProduct(Product product, ProductType type) {
        ProductType productType = productTypeRepository.findByTypeName(type.getName());
        product.setType(productType);
        productRepository.save(product);
    }

    public Product getProductByUUID(UUID uuid) {
        return productRepository.findByUuid(uuid.toString());
    }

    public ProductInfo getProductFullInfo(UUID uuid) throws ServiceException {
        List<Object[]> info = productRepository.getFullInfoByUuid(uuid.toString());
        ProductInfo productInfo = new ProductInfo();
        Product product = productRepository.findByUuid(uuid.toString());
        if(product == null) throw new ServiceException(ErrorCode.NO_PRODUCT_WITH_SUCH_UUID, "no product found with UUID: " + uuid.toString(), null);
        productInfo.manageAttributes(info, product);
        productInfo.setProduct(product);
        return productInfo;
    }

    public Collection<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductByUUID(UUID uuid) {
        productRepository.deleteByUuid(uuid.toString());
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public void addAttribute(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    public Attribute getAttributeByName(String name) {
        return attributeRepository.findByName(name);
    }

    public void addAttributeValue(AttributeValue attributeValue, UUID uuid, Attribute attribute) {
        attributeValue = setUpAttributeValue(attributeValue, uuid, attribute);
        attributeValueRepository.save(attributeValue);
    }

    private AttributeValue setUpAttributeValue(AttributeValue attributeValue, UUID uuid, Attribute attribute) {
        String attributeName = attribute.getName();
        Product product = productRepository.findByUuid(uuid.toString());
        attributeValue.setProduct(product);
        Attribute attributeByName = getAttributeByName(attributeName);
        attributeValue.setAttribute(attributeByName);

        return attributeValue;
    }

    public ProductType getTypeByName(String name) {
        return productTypeRepository.findByTypeName(name);
    }

    public List<Product> getProductsOfType(ProductType type) {
        return productRepository.findByType(type);
    }

    public void addType(ProductType type) {
        productTypeRepository.save(type);
    }

    public void matchTypeAndAttribute(ProductType productType, Attribute attribute) {
        Set<Attribute> set = new HashSet<>();
        set.addAll(productType.getAttributes());
        set.add(attribute);
        productType.setAttributes(set);
        productTypeRepository.save(productType);
    }

    public Set<Attribute> getAttributesOfType(String productTypeName) {
        ProductType productType = productTypeRepository.findByTypeName(productTypeName);
        return productType.getAttributes();
    }

    public void setImageToProduct(URL url, UUID uuid) throws IOException {
        byte[] imageInByte = Tools.imageToByteArray(ImageIO.read(url));
        Product product = getProductByUUID(uuid);
        ProductImage productImage = new ProductImage(product, imageInByte);
        productImageRepository.save(productImage);
    }

    public byte[] getImageForSpecifiedProduct(UUID uuid, int imgNumber){
        Product product = productRepository.findByUuid(uuid.toString());
        ArrayList<ProductImage> productImages = new ArrayList<>(productImageRepository.getImagesForProduct(product));
        return productImages.get(imgNumber).getImage();
    }

    public List<ProductType> getAllProductTypes(){
        return productTypeRepository.findAll();
    }
}