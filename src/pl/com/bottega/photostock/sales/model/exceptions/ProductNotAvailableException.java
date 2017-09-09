package pl.com.bottega.photostock.sales.model.exceptions;


import pl.com.bottega.photostock.sales.model.Product;

public class ProductNotAvailableException extends RuntimeException{

    public ProductNotAvailableException(Product product) {
        super(String.format("Product %s is not available.", product.getNumber()));
    }
}
