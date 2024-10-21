package com.filmfellows.cinemates.app.store;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.service.StoreService;
import com.filmfellows.cinemates.domain.store.model.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService sService;

    @GetMapping("/main")
    public String showStoreMain(Model model) {
        List<String> categories = Arrays.asList("기프트카드", "영화관람권", "콤보", "팝콘", "스낵", "음료");
        model.addAttribute("categories", categories);
        Map<String, List<Product>> productsByCategory = new HashMap<>();
        for (String category : categories) {
            productsByCategory.put(category, sService.getProductsByCategory(category, 4));
            System.out.println("나옴");
        }

        model.addAttribute("productsByCategory", productsByCategory);

        return "store/storeMain";
    }

    @GetMapping("/{category}")
    public String showCategoryProducts(@PathVariable String category, Model model) {
        List<String> categories = Arrays.asList("기프트카드", "영화관람권", "콤보", "팝콘", "스낵", "음료");
        List<Product> products = sService.getProductsByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "store/storeCategory";
    }

    @GetMapping("/product/{productNo}")
    public String getProductDetail(@PathVariable int productNo, Model model) {
        List<String> categories = Arrays.asList("기프트카드", "영화관람권", "콤보", "팝콘", "스낵", "음료");
        Product product = sService.getProductDetail(productNo);
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "store/product-detail";
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        List<String> categories = Arrays.asList("기프트카드", "영화관람권", "콤보", "팝콘", "스낵", "음료");
        List<Cart> cartItems = sService.getCartItems(memberId);
        int totalAmount = 0;
        int totalDiscount = 0;
        int finalPrice = 0;

        for(Cart item : cartItems) {
            Product product = item.getProduct();
            int itemTotalPrice = product.getPrice() * item.getQuantity();
            int itemTotalDiscount = product.getDiscountAmount() * item.getQuantity();

            totalAmount += itemTotalPrice;
            totalDiscount += itemTotalDiscount;
            finalPrice += (itemTotalPrice - itemTotalDiscount);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("finalPrice", finalPrice);
        return "store/cart";
    }

    @PostMapping("/cart/insert")
    @ResponseBody
    public ResponseEntity<?> insertToCart(@RequestBody Map<String, Object> cartData, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        try {
            Cart cartItem = new Cart();
            cartItem.setMemberId(memberId);
            cartItem.setProductNo(Integer.parseInt((String)cartData.get("productNo")));
            cartItem.setQuantity((int)cartData.get("quantity"));

            boolean success = sService.insertOrUpdateCartItem(cartItem);

            if (success) {
                return ResponseEntity.ok(Map.of("success", true, "message", "상품이 장바구니에 추가되었습니다."));
            } else {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "장바구니 추가에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "서버 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<?> updateCart(@RequestBody Map<String, Integer> cartData) {
        Cart cartItem = new Cart();
        cartItem.setCartNo(cartData.get("cartNo"));
        cartItem.setProductNo(cartData.get("productNo"));
        cartItem.setQuantity(cartData.get("quantity"));
        boolean success = sService.updateCartItem(cartItem);
        if(success) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "장바구니가 업데이트되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "장바구니 업데이트에 실패했습니다."));
        }
    }

    @PostMapping("cart/delete")
    @ResponseBody
    public ResponseEntity<?> deleteFromCart(@RequestBody Map<String, Integer> request) {
        Integer cartNo = request.get("cartNo");
        Integer productNo = request.get("productNo");
        Cart cartItem = new Cart();
        cartItem.setCartNo(cartNo);
        cartItem.setProductNo(productNo);
        boolean success = sService.deleteCartItems(cartItem);
        if(success) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "선택한 상품(들)이 삭제되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "상품 삭제에 실패했습니다."));
        }
    }

    @PostMapping("cart/clear")
    @ResponseBody
    public ResponseEntity<?> clearCart(HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        boolean success = sService.clearCart(memberId);
        if(success) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "장바구니가 비워졌습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "장바구니 비우기에 실패했습니다."));
        }
    }

    @GetMapping("/gift/new/{productNo}")
    public String showGiftForProduct(@PathVariable int productNo, Model model, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }

        Gift gift = sService.initializeGift(memberId, List.of(Map.of("productNo", productNo, "quantity", 1)), false);
        session.setAttribute("giftInfo", gift);
        model.addAttribute("gift", gift);
        return "store/gift";
    }

    @GetMapping("/gift/prepare")
    public String prepareGift(Model model, HttpSession session) {
        Gift gift = (Gift) session.getAttribute("giftInfo");
        if (gift == null) {
            return "redirect:/store/main";
        }

        model.addAttribute("gift", gift);
        return "store/gift";
    }

    @PostMapping("/gift/submit")
    public String submitGift(@ModelAttribute Gift gift, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }

        gift.setSenderId(memberId);
        Gift savedGift = sService.saveGift(gift);
        session.setAttribute("giftInfo", savedGift);

        return "redirect:/store/gift/confirm/" + savedGift.getGiftNo();
    }

    @GetMapping("/gift/confirm/{giftNo}")
    public String confirmGift(@PathVariable int giftNo, Model model, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }

        Gift gift = sService.getGiftByNo(giftNo);
        if (gift == null || !gift.getSenderId().equals(memberId)) {
            return "redirect:/error/accessDenied";
        }

        model.addAttribute("gift", gift);
        return "store/gift";
    }

    // 선물받는 회원 정보 확인
    @PostMapping("/gift/set-recipient")
    @ResponseBody
    public ResponseEntity<?> setRecipient(@RequestBody Map<String, String> recipientInfo, HttpSession session) {
        String name = recipientInfo.get("name");
        String phone = recipientInfo.get("phone");
        Gift gift = (Gift) session.getAttribute("giftInfo");

        if (gift == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "선물 정보가 없습니다."));
        }
        boolean success = sService.verifyAndSetRecipient(gift, name, phone);
        if (success) {
            session.setAttribute("giftInfo", gift);
            return ResponseEntity.ok(Map.of("success", true, "message", "수신자 확인 완료"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "일치하는 회원 정보가 없습니다."));
        }
    }

    // 선물하기 결제완료 후 선물 완료여부 업데이트
    @PostMapping("/complete-payment")
    public String completePayment(@RequestParam("giftNo") int giftNo) {
        try {
            // 결제 처리 로직

            // 결제 완료 후 선물 완료여부 업데이트
            sService.completeGiftPayment(giftNo);

            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @RequestMapping(value="/purchase/{purchaseNo}/{productNo}/{quantity}", method={RequestMethod.GET, RequestMethod.POST})
    public String showPurchase(@PathVariable int productNo
            , @PathVariable int quantity
            , @PathVariable int purchaseNo
            ,Model model, HttpSession session) {
        // 로그인한 회원 정보 가져오기
        String memberId = (String) session.getAttribute("memberId");
        List<String> categories = Arrays.asList("기프트카드", "영화관람권", "콤보", "팝콘", "스낵", "음료");
        if (memberId == null) {
            return "redirect:/login";
        }

        // 구매 정보 조회

        Purchase purchase = sService.getPurchaseDetails(purchaseNo);
        Product product = sService.getProductByNo(productNo);
        if (purchase == null || !purchase.getMemberId().equals(memberId)) {
            return "redirect:/error/accessDenied";
        }

        Member member = sService.getMemberById(memberId);
        model.addAttribute("categories", categories);
        model.addAttribute("member", member);  // 회원 정보 모델에 추가
        model.addAttribute("purchase", purchase);
        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);
        return "store/purchase";
    }

    @PostMapping("/purchase/initiate")
    public ResponseEntity<?> initiatePurchase(@RequestBody Map<String, Object> request, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        String action = (String) request.get("action");
        boolean purchaseAll = (boolean) request.getOrDefault("purchaseAll", false);
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");

        try {
            if ("gift".equals(action)) {
                Gift gift = sService.initializeGift(memberId, items, purchaseAll);
                session.setAttribute("giftInfo", gift);
                return ResponseEntity.ok(Map.of("success", true, "redirectUrl", "/store/gift/prepare"));
            } else {
                Purchase purchase = sService.initializePurchase(memberId, items, purchaseAll);
                return ResponseEntity.ok(Map.of("success", true, "redirectUrl"
                        , "/store/purchase/" + purchase.getPurchaseNo()
                        + "/" + items.get(0).get("productNo") + "/" + items.get(0).get("quantity")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "주문 처리 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @PostMapping("/purchase/update")
    @ResponseBody
    public ResponseEntity<?> updatePurchase(@RequestBody Purchase purchase, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null || !purchase.getMemberId().equals(memberId)) {
            return ResponseEntity.badRequest().body("권한이 없습니다.");
        }
        sService.updatePurchase(purchase);
        return ResponseEntity.ok("구매 정보가 업데이트되었습니다.");
    }

    @PostMapping("/payment/complete")
    public ResponseEntity<String> completePayment(@RequestBody Map<String, Object> request, HttpSession session) {
        try {
            String memberId = (String) session.getAttribute("memberId");
            if (memberId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User not logged in");
            }

            // 결제 정보 생성
            Purchase purchase = new Purchase();
            purchase.setMemberId(memberId);
            purchase.setPurchaseDate(new Date());
            purchase.setStatus("COMPLETE");
            purchase.setPaymentMethod(request.get("paymentMethod").toString());
            purchase.setTotalAmount(Integer.parseInt(request.get("paid_amount").toString()));
            purchase.setFinalAmount(Integer.parseInt(request.get("paid_amount").toString()));

            // 상품 정보 조회
            int productNo = Integer.parseInt(request.get("productNo").toString());
            Product product = sService.getProductByNo(productNo);

            // 구매 아이템 정보 생성
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setProductNo(productNo);
            purchaseItem.setQuantity(Integer.parseInt(request.get("quantity").toString()));
            purchaseItem.setProduct(product);
            purchaseItem.setPurchasePrice(product.getPrice());
            purchaseItem.setPurchaseDiscountAmount(product.getDiscountAmount());

            // 구매 정보에 아이템 추가
            purchase.setPurchaseItems(Collections.singletonList(purchaseItem));

            // 구매 처리 및 티켓 카운트 증가 (영화관람권인 경우에만)
            sService.processPurchase(purchase);

            return ResponseEntity.ok("Payment completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment : " + e.getMessage());
        }
    }
}