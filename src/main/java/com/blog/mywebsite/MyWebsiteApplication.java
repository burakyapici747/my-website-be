package com.blog.mywebsite;

import com.blog.mywebsite.model.Role;
import com.blog.mywebsite.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyWebsiteApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;

    public MyWebsiteApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(MyWebsiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Role role = new Role(com.blog.mywebsite.enumerator.Role.ADMIN.getValue());
//		roleRepository.save(role);
	}
}
