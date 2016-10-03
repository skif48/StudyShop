package unit;

import com.shop.domain.entity.*;
import com.shop.repository.products.AttributeRepository;
import com.shop.repository.products.AttributeValueRepository;
import com.shop.repository.products.ProductRepository;
import com.shop.repository.products.ProductTypeRepository;
import com.shop.service.shop.ProductInfo;
import com.shop.service.shop.ShopService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stubs.AttributeRepositoryStub;
import stubs.AttributeValueRepositoryStub;
import stubs.ProductRepositoryStub;
import stubs.ProductTypeRepositoryStub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 22.08.2016.
 */
public class ShopServiceUnitTest {
    /*private ShopService shopService;
    private Product product;
    private Attribute attribute;
    private ProductType productType;
    private UUID uuid;
    private Manufacturer manufacturer;
    private AttributeValue attributeValue;
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;
    private AttributeRepository attributeRepository;
    private AttributeValueRepository attributeValueRepository;

    @Before
    public void setUp() throws Exception{
        uuid = UUID.fromString("4b9293b8-6386-498a-8b7b-cceaa1d1dc36");
        product = new Product(uuid, new ProductType("PHONE"), "Zenfone 2", new Manufacturer("Asus"));
        attribute = new Attribute("diagonal");
        manufacturer = new Manufacturer("Asus");
        productType = new ProductType("PHONE");
        attributeValue = new AttributeValue(product, attribute, "5.5");

        productTypeRepository = new ProductTypeRepositoryStub();
        attributeRepository = new AttributeRepositoryStub();
        attributeValueRepository = new AttributeValueRepositoryStub();
        productRepository = new ProductRepositoryStub(attributeRepository, attributeValueRepository);

        shopService = new ShopService();
        shopService.setProductRepository(productRepository);
        shopService.setProductTypeRepository(productTypeRepository);
        shopService.setAttributeRepository(attributeRepository);
        shopService.setAttributeValueRepository(attributeValueRepository);

        shopService.addType(productType);
        shopService.addAttribute(attribute);
        shopService.putProduct(product);
        shopService.addAttributeValue(attributeValue, uuid, attribute);
    }

    @Test
    public void getProductFullInfoForUUIDReturnsValidProductInfo() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProduct(product);
        productInfo.setAttributeValueMap(new HashMap<>());
        productInfo.getAttributeValueMap().put(attribute, attributeValue);

        ProductInfo test = shopService.getProductFullInfo(uuid);
        Assert.assertTrue(productInfo.equals(test));
    }

    @Test
    public void whenAttributeIsMatchedToProductTypeItIsStoredInAttributesSetInProductType() throws Exception {
        shopService.matchTypeAndAttribute(productType, attribute);
        ArrayList<Attribute> attributes = new ArrayList<>(productType.getAttributes());
        Assert.assertEquals(attributes.get(0), attribute);
    }

*/
}
