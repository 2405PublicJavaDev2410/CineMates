package com.filmfellows.cinemates.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Getter
public class FileConfig implements WebMvcConfigurer {
        public static final String FOLDER_PATH = "/cinemates/";

        /**
         * 담당자 : 엄태운
         * 회원 파일 경로
         */
        @Value("${member.images.path}")
        private String memberImagePath;

        /** 담당자 : another
         *  영화 파일 경로
         */
        @Value("${another.images.path}")
        private String anotherImagePath;




        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                String webPath = FOLDER_PATH + "**";
                /**
                 * 회원
                 */
                registry.addResourceHandler(webPath)
                        .addResourceLocations(memberImagePath);
                /**
                 * another
                 */
                registry.addResourceHandler(webPath)
                        .addResourceLocations(anotherImagePath);
        }
}