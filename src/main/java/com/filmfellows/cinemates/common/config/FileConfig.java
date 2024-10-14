package com.filmfellows.cinemates.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@Getter
public class FileConfig implements WebMvcConfigurer {
        public static final String FOLDER_PATH = "/cinemates";

        /**
         * 담당자 : 엄태운
         * 회원 파일 경로
         */
        @Value("${member.images.path}")
        private String memberImagePath;

        /**
         * 담당자 : 엄태운
         * 문의 파일 경로
         */
        @Value("${qna.images.path}")
        private String qnaImagePath;

        /**
         * 담당자 : another
         * 영화 파일 경로
         */
        @Value("${another.images.path}")
        private String anotherImagePath;


        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler(FOLDER_PATH + "/member/**")
                        .addResourceLocations(memberImagePath);

                registry.addResourceHandler(FOLDER_PATH + "/qna/**")
                        .addResourceLocations(qnaImagePath);

                registry.addResourceHandler(FOLDER_PATH + "/another/**")
                        .addResourceLocations(anotherImagePath);
        }
}