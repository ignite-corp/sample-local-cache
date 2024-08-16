package co.kr.ignite.samplelocalcache.banner.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.kr.ignite.samplelocalcache.banner.domain.Banner;
import co.kr.ignite.samplelocalcache.banner.domain.BannerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/banners")
@RestController
public class BannerController {
    private final BannerRepository bannerRepository;

    @GetMapping
    public List<Banner> getAllDisplayableBanners() {
        return bannerRepository.findAllDisplayableBanners();
    }
}
