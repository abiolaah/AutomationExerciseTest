package products;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import baseTests.BaseTest;
import pages.CartPage;
import pages.ProductsPage;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerifyProductsTest extends BaseTest {
    protected ProductsPage productsPage;
    protected CartPage cartPage;
    @Test
    @DisplayName("Confirm Page Header")
    @Order(1)
    public void pageHeaderConfirmation(){
        productsPage = homePage.clickProductNavigation();
        assertThat("Page header text should read 'All Products'", productsPage.pageHeaderTextValue(), equalToIgnoringCase("All Products"));
    }

    @Test
    @DisplayName("Confirm Page Header Change After Search")
    @Order(2)
    public void pageHeaderSearchChangeConfirmation(){
        productsPage = homePage.clickProductNavigation();
        assertThat("Page header text should reads 'Searched Products'", productsPage.searchHeaderTextValue("Dress"), equalToIgnoringCase("Searched Products"));
    }


    @ParameterizedTest(name = "Confirm Page Header Change After {0} Filter")
    @MethodSource("provideCategoryFilterParameters")
    @DisplayName("Confirm Page Header Change After Category Filter")
    @Order(3)
    public void pageHeaderFilterChangeConfirmation(String filterType, String expectedHeaderText) {
        productsPage = homePage.clickProductNavigation();
        String actualHeaderText = switch (filterType) {
            case "Women Dress" -> productsPage.confirmHeaderTextChangeForWomenDress();
            case "Women Tops" -> productsPage.confirmHeaderTextForWomenTops();
            case "Women Saree" -> productsPage.confirmHeaderTextForWomenSaree();
            case "Men TShirt" -> productsPage.confirmHeaderTextForMenTShirts();
            case "Men Jeans" -> productsPage.confirmHeaderTextForMenJean();
            case "Kids Dress" -> productsPage.confirmHeaderTextForKidDress();
            case "Kids Tops and Shirt" -> productsPage.confirmHeaderTextForKidTop();
            default -> throw new IllegalArgumentException("Invalid filter type: " + filterType);
        };

        assertThat("Page Header text should contain '" + expectedHeaderText + "'", actualHeaderText, containsStringIgnoringCase(expectedHeaderText));
    }

    @ParameterizedTest(name = "Confirm Page Header Change After {0} Brand Filter")
    @MethodSource("provideBrandFilterParameters")
    @DisplayName("Confirm Page Header Change Based on Brand Filter")
    @Order(4)
    public void pageHeaderBrandFilterChangeConfirmation(String brandFilterType, String expectedHeaderText) {
        productsPage = homePage.clickProductNavigation();
        String actualHeaderText = switch (brandFilterType) {
            case "Polo" -> productsPage.confirmHeaderForPoloBrandFilter();
            case "H&M" -> productsPage.confirmHeaderForHMBrandFilter();
            case "Madame" -> productsPage.confirmHeaderForMadameBrandFilter();
            case "Mast" -> productsPage.confirmHeaderForMastBrandFilter();
            case "BabyHug" -> productsPage.confirmHeaderForBabyHugBrandFilter();
            case "Allen" -> productsPage.confirmHeaderForAllenBrandFilter();
            case "Kookie" -> productsPage.confirmHeaderForKookieBrandFilter();
            case "Biba" -> productsPage.confirmHeaderForBibaBrandFilter();
            default -> throw new IllegalArgumentException("Invalid brand filter type: " + brandFilterType);
        };

        assertThat("Page Header text should contain '" + expectedHeaderText + "'", actualHeaderText, containsStringIgnoringCase(expectedHeaderText));
    }

    private static Stream<Arguments> provideCategoryFilterParameters() {
        return Stream.of(
                Arguments.of("Women Dress", "Dress"),
                Arguments.of("Women Tops", "Women - Tops"),
                Arguments.of("Women Saree", "SAREE"),
                Arguments.of("Men TShirt", "Men - TShirt"),
                Arguments.of("Men Jeans", "Men - Jeans"),
                Arguments.of("Kids Dress", "DRESS"),
                Arguments.of("Kids Tops and Shirt", "Kids - Tops & Shirts")
        );
    }

    private static Stream<Arguments> provideBrandFilterParameters() {
        return Stream.of(
                Arguments.of("Polo", "Brand - POLO"),
                Arguments.of("H&M", "Brand - H&M"),
                Arguments.of("Madame", "Brand - MADAME"),
                Arguments.of("Mast", "Brand - MAST & HARBOUR"),
                Arguments.of("BabyHug", "Brand - BABYHUG"),
                Arguments.of("Allen", "Brand - ALLEN"),
                Arguments.of("Kookie", "Brand - KOOKIE KIDS"),
                Arguments.of("Biba", "Brand - BIBA")
        );
    }

    @Test
    @DisplayName("Confirm Cart Modal Header Text")
    @Order(5)
    public void verifyAddToCartModalHeaderText(){
        productsPage = homePage.clickProductNavigation();
        productsPage.clickAddToCartFilterButton();
        String modalHeaderText = productsPage.modalContentHeaderText();
        String expected = "Added!";
        assertThat("The Modal Header Text should contains expected text", modalHeaderText, equalToIgnoringCase(expected));
    }

    @Test
    @DisplayName("Confirm Cart Modal Body Text")
    @Order(6)
    public void verifyAddToCartModalBodyText(){
        productsPage = homePage.clickProductNavigation();
        productsPage.clickAddToCartFilterButton();
        List<String> modalBodyText = productsPage.modalContentBodyText();
        String expected = "Your product has been added to cart.";
        assertThat("The Modal Body Text should contains expected text", modalBodyText, hasItem(containsStringIgnoringCase(expected)));
    }

    @Test
    @DisplayName("Confirm Cart Modal Footer Text")
    @Order(7)
    public void verifyAddToCartModalFooterText(){
        productsPage = homePage.clickProductNavigation();
        productsPage.clickAddToCartFilterButton();
        String modalFooterText = productsPage.modalContentFooterText();
        String expected = "Continue Shopping";
        assertThat("The Modal Footer Text should contains expected text", modalFooterText, equalToIgnoringCase(expected));
    }

    @Test
    @DisplayName("Confirm Page remains in Product Page")
    @Order(8)
    public void verifyAddToCartModalContinueButton() {
        productsPage = homePage.clickProductNavigation();
        productsPage.clickAddToCartFilterButton();
        productsPage.clickModalFooterContinueButton();
        String currentUrl = productsPage.getCurrentUrl();
        assertThat("The page is still the product page after the clicking continue page", currentUrl, containsStringIgnoringCase("products"));
    }

    @Test
    @DisplayName("Confirm Page route to Cart Page")
    @Order(9)
    public void verifyAddToCartModalViewCartButton(){
        productsPage = homePage.clickProductNavigation();
        productsPage.clickAddToCartFilterButton();
        cartPage = productsPage.clickModalContentViewCartButton();
        String shoppingText = cartPage.getBreadCrumb();
        assertThat("The page is the cart page", shoppingText, equalToIgnoringCase("Shopping Cart"));
    }

}
