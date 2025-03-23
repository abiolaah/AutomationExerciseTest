package utils;

public class CartProduct {
    private final String name;
    private final String price;
    private final String quantity;

    public CartProduct(String name, String price, String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartProduct{name='" + name + "', price='" + price + "', quantity='" + quantity + "'}";
    }
}
