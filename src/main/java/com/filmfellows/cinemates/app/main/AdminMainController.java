package com.filmfellows.cinemates.app.main;

import com.filmfellows.cinemates.app.main.dto.BannerDTO;
import com.filmfellows.cinemates.domain.main.model.service.MainService;
import com.filmfellows.cinemates.domain.main.model.vo.Banner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminMainController {
    private final MainService mainService;

    @GetMapping("/admin/banner")
    public String showBanner(Model model) {
        List<BannerDTO> bList = mainService.getBannerList();
        model.addAttribute("bList", bList);
        return "pages/main/adminBanner";
    }

    @GetMapping("/admin/banner-detail/{bannerNo}")
    public String showMainBannerDetail(@PathVariable Long bannerNo, Model model) {
        List<BannerDTO> bList = mainService.getBannerDetail(bannerNo);
        model.addAttribute("bList", bList);
        return "pages/main/adminBannerDetail";
    }

    @GetMapping("/admin/insert-banner")
    public String showInsertBanner() {
        return "pages/main/adminInsertBanner";
    }

    @PostMapping("/admin/insertBanner")
    public String addBanner(BannerDTO bannerDTO) {
        Banner banner = new Banner();
        banner.setBannerTitle(bannerDTO.getBannerTitle());
        banner.setBannerContent(bannerDTO.getBannerContent());
        banner.setBannerUrl(bannerDTO.getBannerUrl());
        banner.setLinkUrl(bannerDTO.getLinkUrl());
        banner.setStartDate(bannerDTO.getStartDate());
        banner.setEndDate(bannerDTO.getEndDate());
        banner.setPageType(bannerDTO.getPageType());
        banner.setIsActive(bannerDTO.getIsActive());

        mainService.addBanner(banner);
        return "redirect:/admin/banner";
    }

    @PostMapping("/admin/update-banner")
    public String updateBanner(@ModelAttribute BannerDTO bannerDTO) {
        mainService.updateBanner(bannerDTO);
        return "redirect:/admin/banner";
    }

    @GetMapping("/admin/remove-banner")
    public String removeBanner(Long bannerNo) {
        mainService.removeBanner(bannerNo);
        return "redirect:/admin/banner";
    }
}