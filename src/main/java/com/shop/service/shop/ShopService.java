package com.shop.service.shop;

import com.shop.domain.entity.*;
import com.shop.error.ErrorCode;
import com.shop.error.ServiceException;
import com.shop.repository.products.*;
import com.shop.service.dataUtils.ManufacturerRequest;
import com.shop.service.dataUtils.ProductRequest;
import com.shop.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
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

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private EnumerableAttributeValueRepository enumerableAttributeValueRepository;

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

    public UUID putProduct(ProductRequest productRequest) {
        Product product = new Product();
        ProductType type = getTypeByName(productRequest.getType().getName());
        Manufacturer manufacturer = getManufacturerByName(productRequest.getManufacturer().getName());

        product.setLabel(productRequest.getLabel());
        product.setType(type);
        product.setManufacturer(manufacturer);
        product.setPrice(Double.parseDouble(productRequest.getPrice()));
        UUID uuid = UUID.randomUUID();
        product.setUuid(uuid.toString());
        product = productRepository.save(product);

        long imageID = productRequest.getImageID();
        ProductImage image = productImageRepository.findOne(imageID);
        image.setProduct(product);

        Map<Attribute, AttributeValue> attributeValueMap = new HashMap<>();
        for(Attribute attribute : productRequest.getAttributes().keySet()){
            Attribute temp = attributeRepository.findByName(attribute.getName());
            attributeValueMap.put(temp, new AttributeValue(productRepository.findByUuid(uuid.toString()), temp, productRequest.getAttributes().get(attribute).getValue()));
        }

        for(Attribute attribute : productRequest.getEnumerableAttributes().keySet()){
            Attribute temp = attributeRepository.findByName(attribute.getName());
            List<Object> enumerableAttributeValuesByName = new ArrayList<>(enumerableAttributeValueRepository.findEnumerableAttributeValueByName(temp.getName()));
            for(Object obj : enumerableAttributeValuesByName){
                Long id = Long.parseLong(obj.toString());
                EnumerableAttributeValue enumerableAttributeValue = enumerableAttributeValueRepository.findOne(id);
                if(enumerableAttributeValue.getValue().equals(productRequest.getEnumerableAttributes().get(attribute))){
                    attributeValueMap.put(temp, new AttributeValue(productRepository.findByUuid(uuid.toString()), temp, enumerableAttributeValue));
                }
            }
        }

        for(AttributeValue value : attributeValueMap.values()){
            attributeValueRepository.save(value);
        }

        return uuid;
    }

    public Product getProductByUUID(UUID uuid) {
        return productRepository.findByUuid(uuid.toString());
    }

    public ProductInfo getProductFullInfo(UUID uuid) throws ServiceException {
        List<Object[]> info = productRepository.getFullInfoByUuid(uuid.toString());
        ProductInfo productInfo = new ProductInfo();
        Product product = productRepository.findByUuid(uuid.toString());
        if(product == null) throw new ServiceException(ErrorCode.NO_PRODUCT_WITH_SUCH_UUID, "no product found with UUID: " + uuid.toString(), null);
        List<Attribute> attributes = attributeRepository.findAll();
        List<String> enumerableAttributesNames = new ArrayList<>();
        for (Attribute attribute : attributes){
            if(!attribute.getEnumerableAttributeValueSet().isEmpty()){
                enumerableAttributesNames.add(attribute.getName());
            }
        }
        Map<String, String> enumerableAttributesValuesMap = new HashMap<>();
        for (String name : enumerableAttributesNames){
            String element = enumerableAttributeValueRepository.findEnumerableAttributeValueOfProductByUUIDAndName(name, product.getUuid());
            if(element != null)
                enumerableAttributesValuesMap.put(name, element);
        }
        productInfo.manageAttributes(info, product, enumerableAttributesValuesMap);
        productInfo.setProduct(product);
        return productInfo;
    }

    public Collection<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductInfo> getAllProductsInfo() throws ServiceException {
        ArrayList<Product> products = new ArrayList<>(productRepository.findAll());
        ArrayList<ProductInfo> infos = new ArrayList<>();
        for(Product p : products){
            infos.add(getProductFullInfo(UUID.fromString(p.getUuid())));
        }

        return infos;
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

    public void setImageToProductByURL(URL url, UUID uuid) throws IOException {
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

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerByName(String name) {
        return manufacturerRepository.findByName(name);
    }

    public long addImageByFile(MultipartFile uploadFile, UUID uuid) throws IOException {
        ProductImage image;
        if(uuid != null) {
            image = new ProductImage(productRepository.findByUuid(uuid.toString()), uploadFile.getBytes());
        } else {
            image = new ProductImage(null, uploadFile.getBytes());
        }
        image = productImageRepository.save(image);
        return image.getId();
    }

    public void addManufacturer(ManufacturerRequest manufacturerRequest){
        Set<ProductType> types = new HashSet<>();
        for(ProductType type : manufacturerRequest.getTypes()){
            types.add(productTypeRepository.findByTypeName(type.getName()));
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerRequest.getName());
        manufacturer.setTypeSet(types);
        manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getManufacturersOfType(String type) {
        ProductType productType = productTypeRepository.findByTypeName(type);
        return new ArrayList<>(productType.getManufacturers());
    }
}