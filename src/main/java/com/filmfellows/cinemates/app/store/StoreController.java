package com.filmfellows.cinemates.app.store;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.service.StoreService;
import com.filmfellows.cinemates.domain.store.model.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
        List<String> allCategories = Arrays.asList("기프트카드", "영화관람권", "콤보", "팝콘", "스낵", "음료");
        List<Product> products = sService.getProductsByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("allCategories", allCategories);
        return "store/storeCategory";
    }

    @GetMapping("/product/{productNo}")
    public String getProductDetail(@PathVariable int productNo, Model model) {
        Product product = sService.getProductDetail(productNo);
        model.addAttribute("product", product);
        return "store/product-detail";
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
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
    public ResponseEntity<?> updateCart(@RequestBody Cart cartItem) {
        boolean success = sService.updateCartItem(cartItem);
        if(success) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "장바구니가 업데이트되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "장바구니 업데이트에 실패했습니다."));
        }
    }

    @PostMapping("cart/delete")
    @ResponseBody
    public ResponseEntity<?> deleteFromCart(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> cartNos = request.get("cartNos");
        boolean success = sService.deleteCartItems(cartNos);
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

/*    @PostMapping("/cart/proceed")
    public String proceedFromCart(@RequestParam List<Integer> selectedItems
            , @RequestParam("action") String action
            , HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        List<Cart> selectedCarts = sService.getSelectedCartItems(memberId, selectedItems);

        if ("gift".equals(action)) {
            Gift gift = sService.initializeGiftFromCart(memberId, selectedCarts);
            session.setAttribute("giftInfo", gift);
            return "redirect:/store/gift/prepare";
        } else if ("purchase".equals(action)) {
            Purchase purchase = new Purchase();
            purchase.setMemberId(memberId);
            purchase.setItems(selectedCarts.stream()
                    .map(cart -> {
                        PurchaseItem item = new PurchaseItem();
                        item.setProductNo(cart.getProductNo());
                        item.setQuantity(cart.getQuantity());
                        item.setProduct(cart.getProduct());
                        return item;
                    })
                    .collect(Collectors.toList()));
            session.setAttribute("purchaseInfo", purchase);
            return "redirect:/store/purchase";
        } else {
            return "redirect:/store/cart";
        }
    }*/

    @PostMapping("/cart/proceed")
    public ResponseEntity<?> proceedFromCart(@RequestBody Map<String, Object> request, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        String action = (String) request.get("action");
        List<Cart> selectedCarts;

        if (request.containsKey("all") && (boolean) request.get("all")) {
            selectedCarts = sService.getCartItems(memberId);
        } else {
            List<Integer> selectedItems = (List<Integer>) request.get("selectedItems");
            selectedCarts = sService.getSelectedCartItems(memberId, selectedItems);
        }

        if ("gift".equals(action)) {
            Gift gift = sService.initializeGiftFromCart(memberId, selectedCarts);
            session.setAttribute("giftInfo", gift);
            return ResponseEntity.ok(Map.of("success", true, "redirectUrl", "/store/gift/prepare"));
        } else if ("purchase".equals(action)) {
            Purchase purchase = sService.initializePurchaseFromCart(memberId, selectedCarts);
            session.setAttribute("purchaseInfo", purchase);
            return ResponseEntity.ok(Map.of("success", true, "redirectUrl", "/store/purchase/" + purchase.getPurchaseNo()));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "잘못된 요청입니다."));
        }
    }

    @GetMapping("/gift/{productNo}")
    public String showGiftForProduct(@PathVariable int productNo
            , Model model
            , HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }

        Gift gift = sService.initializeGift(memberId, productNo, 1);
        session.setAttribute("giftInfo", gift);
        model.addAttribute("gift", gift);
        return "store/gift";
    }

    @GetMapping("gift/prepare")
    public String prepareGift(Model model, HttpSession session) {
        Gift gift = (Gift) session.getAttribute("giftInfo");
        if (gift == null) {
            return "redirect:/store/main";
        }

        model.addAttribute("gift", gift);

        return "store/gift";
    }

    @PostMapping("/gift/initiate")
    public String initiateGift(@RequestParam("productNo") int productNo
            , @RequestParam("quantity") int quantity
            , HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        Gift gift = sService.initializeGift(memberId, productNo, quantity);
        session.setAttribute("giftInfo", gift);
        return "redirect:/store/gift/prepare";
    }

    // 선물받는 회원 정보 확인
    @PostMapping("/gift/set-recipient")
    @ResponseBody
    public ResponseEntity<?> setRecipient(@RequestBody Map<String, String> recipientInfo
            , HttpSession session) {
        String name = recipientInfo.get("name");
        String phone = recipientInfo.get("phone");
        Gift gift = (Gift) session.getAttribute("giftInfo");

        if (gift == null) {
            return ResponseEntity.badRequest().body(Map.of("succtess", false, "message", "선물 정보가 없습니다."));

        }
        boolean success = sService.verifyAndSerRecipient(gift, name, phone);
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

    @RequestMapping(value="/purchase/{purchaseNo}", method={RequestMethod.GET, RequestMethod.POST})
    public String showPurchase(@PathVariable int purchaseNo
            , Model model, HttpSession session) {
        // 로그인한 회원 정보 가져오기
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }

        // 구매 정보 조회
        Purchase purchase = sService.getPurchaseDetails(purchaseNo);
        // 구매 정보의 memberId와 로그인한 회원의 ID가 일치하는지 확인
        if (purchase == null || !purchase.getMemberId().equals(memberId)) {
            // 일치하지 않으면 에러 페이지 리다이렉트
            return "redirect:/error/accessDenied";
        }

        Member orderer = sService.getMemberById(memberId);

        model.addAttribute("purchase", purchase);
        model.addAttribute("orderer", orderer);

        return "store/purchase";
    }

    @PostMapping("/purchase/initiate")
    public String initiatePurchase(@RequestParam("productNo") int productNo, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        Purchase purchase = sService.initializePurchase(memberId, productNo);
        session.setAttribute("purchaseInfo", purchase);
        return "redirect:/store/purchase/" + purchase.getPurchaseNo();
    }

/*    // 결제 준비
    @PostMapping("/prepare-payment")
    @ResponseBody
    public ResponseEntity<?> preparePayment(@RequestBody Map<String, Object> paymentInfo, HttpSession session) {
        try {
            boolean giftYn = (boolean) paymentInfo.get("giftYn");
            KakaoPayReady kakaoPayReady;
            if (giftYn) {
                Gift gift = (Gift) session.getAttribute("giftInfo");
                if (gift == null) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Gift information not found"));
                }
                kakaoPayReady = sService.prepareGiftPayment(gift);
            } else {
                Purchase purchase = (Purchase) session.getAttribute("purchaseInfo");
                if (purchase == null) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Purchase information not found"));
                }
                kakaoPayReady = sService.preparePurchasePayment(purchase);
            }

            // 결제 준비 정보를 세션에 저장
            StorePayment storePayment = new StorePayment();
            storePayment.setPurchaseType(giftYn ? "GIFT" : "PURCHASE");
            storePayment.setAmount(giftYn ? ((Gift)session.getAttribute("giftInfo")).getFinalAmount()
                    : ((Purchase)session.getAttribute("purchaseInfo")).getFinalAmount());
            storePayment.setPaymentStatus("READY");
            storePayment.setPaymentKey(kakaoPayReady.getTid());
            session.setAttribute("storePayment", storePayment);

            return ResponseEntity.ok().body(kakaoPayReady);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 결제 완료
    @PostMapping("/complete-payment")
    public String completePayment(@RequestParam("pg_token") String pgToken,
                                  HttpSession session) {
        try {
            StorePayment storePayment = (StorePayment) session.getAttribute("storePayment");
            if (storePayment == null) {
                return "redirect:/store/payment-failure";
            }

            boolean giftYn = "GIFT".equals(storePayment.getPurchaseType());
            KakaoPayApproval approval;

            if (giftYn) {
                Gift gift = (Gift) session.getAttribute("giftInfo");
                if (gift == null) {
                    return "redirect:/store/gift";
                }
                approval = sService.completeGiftPayment(gift, pgToken, storePayment.getPaymentKey());
                sService.processGiftSend(gift);
                session.removeAttribute("giftInfo");
            } else {
                Purchase purchase = (Purchase) session.getAttribute("purchaseInfo");
                if (purchase == null) {
                    return "redirect:/store/cart";
                }
                approval = sService.completePurchasePayment(purchase, pgToken, storePayment.getPaymentKey());
                session.removeAttribute("purchaseInfo");
            }

            // 결제 완료 처리
            storePayment.setPaymentStatus("COMPLETED");
            storePayment.setPaymentDate(new Date());
            sService.updateStorePayment(storePayment);

            session.removeAttribute("storePayment");

            return giftYn ? "redirect:/store/gift-confirmation" : "redirect:/store/payment-success";
        } catch (Exception e) {
            // 에러 처리
            return "redirect:/store/payment-failure";
        }
    }*/
}