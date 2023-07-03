package com.viettel.intern.ultis;

import com.viettel.intern.entity.base.Authority;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.repository.base.AuthorityRepository;
import com.viettel.intern.repository.base.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@Slf4j
//@Component
@AllArgsConstructor
public class GenerateInitData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) {
        log.info("== Generate init data ==");
        if (!userRepository.existsById(1L)) {
            String createdBy = "SYSTEM";

            Authority adminRole = Authority.builder().id(1L).name("ADMIN").status(1).build();
            adminRole.setCreatedBy(createdBy);
            Authority userRole = Authority.builder().id(2L).name("USER").status(1).build();
            userRole.setCreatedBy(createdBy);

            authorityRepository.saveAll(List.of(adminRole, userRole));

            //pw: 123456
            User admin = User.builder()
                    .id(1L)
                    .username("admin")
                    .password("$2a$12$dUdoq.0dYwQBQy.y82YzQuj88g2lf5ukz4yc.JBWB2cjS7/V5Zt/u")
                    .email("admin@localhost.com")
                    .status(1)
                    .authorities(List.of(adminRole))
                    .build();
            admin.setCreatedBy(createdBy);

            User user = User.builder()
                    .id(2L)
                    .username("user")
                    .email("user@localhost.com")
                    .password("$2a$12$dUdoq.0dYwQBQy.y82YzQuj88g2lf5ukz4yc.JBWB2cjS7/V5Zt/u")
                    .status(1)
                    .authorities(List.of(userRole))
                    .build();
            user.setCreatedBy(createdBy);

            userRepository.saveAll(List.of(admin, user));
        }
    }
}
