package com.sulsul.suldaksuldak.component;

import com.sulsul.suldaksuldak.dto.admin.user.AdminUserDto;
import com.sulsul.suldaksuldak.repo.admin.auth.AdminUserRepository;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartUpEventListener implements ApplicationListener<ServletWebServerInitializedEvent> {
    private final AdminUserRepository adminUserRepository;

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        try {
            List<AdminUserDto> adminUserDtos = adminUserRepository.findAllAdminUser();
            if (adminUserDtos.isEmpty()) {
                log.info("NOT FOUND ADMIN USER");
                log.info("CREATE USER.........");
                adminUserRepository.save(
                        AdminUserDto.of(
                                null,
                                "admin",
                                UtilTool.encryptPassword("admin", "admin"),
                                "ADMIN"
                        ).toEntity()
                );
            }
        } catch (Exception e) {
            log.info("[[[[ {} ]]]]", e.getMessage());
        }
    }
}
