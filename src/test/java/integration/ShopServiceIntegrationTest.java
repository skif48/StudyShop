package integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.shop.ShopApplication;
import com.shop.domain.entity.Manufacturer;
import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductType;
import com.shop.domain.user.User;
import com.shop.repository.products.AttributeRepository;
import com.shop.repository.products.AttributeValueRepository;
import com.shop.repository.products.ProductRepository;
import com.shop.repository.products.ProductTypeRepository;
import com.shop.service.shop.ShopService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 12.08.2016.
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShopApplication.class)
@DatabaseSetup(ShopServiceIntegrationTest.PRODUCTS_DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { ShopServiceIntegrationTest.PRODUCTS_DATASET})
@DirtiesContext
public class ShopServiceIntegrationTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    private ShopService shopService;
    private Product product;
    private User user;
    private UUID uuid;
    private Manufacturer manufacturer;

    protected static final String PRODUCTS_DATASET              = "classpath:datasets/products.xml";
    protected static final String PRODUCTTYPE_DATASET           = "classpath:datasets/producttype.xml";
    protected static final String ATTRIBUTES_DATASET            = "classpath:datasets/attributes.xml";
    protected static final String ATTRIBUTE_VALUE_DATASET       = "classpath:datasets/attribute_value.xml";
    protected static final String PRODUCTTYPE_ATTRIBUTE_DATASET = "classpath:datasets/producttype_attribute.xml";

    @Before
    public void setUp() throws Exception{
        uuid = UUID.fromString("8f4622e6-91f3-474a-ba4d-df92a40a60a8");

        manufacturer = new Manufacturer("Asus");

        shopService = new ShopService();
        shopService.setProductRepository(productRepository);
        shopService.setProductTypeRepository(productTypeRepository);
        shopService.setAttributeRepository(attributeRepository);
        shopService.setAttributeValueRepository(attributeValueRepository);

        //product = new Product(uuid, new ProductType("PHONE"), "Zenfone 2", manufacturer);
    }
}
