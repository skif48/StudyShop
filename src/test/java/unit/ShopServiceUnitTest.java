package unit;

import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductType;
import com.shop.repository.products.ProductRepository;
import com.shop.repository.products.ProductTypeRepository;
import com.shop.service.shopServices.ShopService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stubs.ProductRepositoryStub;
import stubs.ProductTypeRepositoryStub;

import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 22.08.2016.
 */
public class ShopServiceUnitTest {
    private ShopService shopService;
    private Product product;
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;

    @Before
    public void setUp() throws Exception{
        productRepository = new ProductRepositoryStub();
        productTypeRepository = new ProductTypeRepositoryStub();

        productTypeRepository.save(new ProductType("PHONE"));

        shopService = new ShopService();
        shopService.setProductRepository(productRepository);
        shopService.setProductTypeRepository(productTypeRepository);

        product = new Product(UUID.fromString("4b9293b8-6386-498a-8b7b-cceaa1d1dc36"), new ProductType("PHONE"), "Asus Zenfone 2");
    }

    @Test
    public void whenPutProductIsCalledThenProductIsStoredInRepo() throws Exception {
        shopService.putProduct(product, product.getType().getName());
        Assert.assertEquals(shopService.getAllProducts().size(), 1);
        Assert.assertEquals(shopService.getProductByUUID(UUID.fromString(product.getUuid())), product);
    }
}
