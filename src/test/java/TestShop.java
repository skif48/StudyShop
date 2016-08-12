import com.shop.Product;
import com.shop.ShopRepository;
import com.shop.ShopService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 12.08.2016.
 */
public class TestShop {
    ShopService shopService;
    ShopRepository shopRepository;
    Product product;

    @Before
    public void setUp() throws Exception{
        shopService = new ShopService();
        shopRepository = new ShopRepository();
        shopService.setShopRepository(shopRepository);
        product = new Product(UUID.randomUUID(), Product.Type.PHONE, "Asus Zenfone 2");
    }

    @Test
    public void whenProductStoredInRepoHttpStatusOKIsReturned() throws Exception{
        Assert.assertEquals(shopService.putProduct(product), HttpStatus.OK);
    }

    @Test
    public void whenProductStoredInRepoIsNullHttpStatusBADREQUESTIsReturned() throws Exception{
        Assert.assertEquals(shopService.putProduct(null), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void whenProductIsAccessedByValidUUIDItIsReturned() throws Exception{
        shopService.putProduct(product);
        Assert.assertEquals(shopService.getProductByUUID(this.product.getUuid()), product);
    }

    @Test
    public void whenAllProductsAreAccessedFromTheRepoTheArrayListIsReturned() throws Exception{
        shopService.putProduct(product);
        ArrayList<Product> arrayList = new ArrayList<>();
        arrayList.add(product);
        Assert.assertEquals(shopService.getAllProducts(), arrayList);
    }

    @Test
    public void whenProductIsDeletedByUUIDItIsRemovedFromTheRepoAndHttpStatusOKIsReturned() throws Exception{
        shopService.putProduct(product);
        HttpStatus status = shopService.deleteProductByUUID(product.getUuid());
        Assert.assertEquals(shopService.getAllProducts().size(), 0);
        Assert.assertEquals(HttpStatus.OK, status);
    }
}
