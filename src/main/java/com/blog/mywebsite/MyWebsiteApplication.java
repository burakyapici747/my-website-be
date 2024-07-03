package com.blog.mywebsite;

import com.blog.mywebsite.model.Role;
import com.blog.mywebsite.repository.RoleRepository;
import com.blog.mywebsite.service.UserService;
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
		var context = SpringApplication.run(MyWebsiteApplication.class, args);
		var bean = context.getBean("articleController");
		System.out.println("uzayli" + bean.getClass());
	}

	@Override
	public void run(String... args) throws Exception {
		//Role role = new Role(com.blog.mywebsite.enumerator.Role.USER.getValue());
		//roleRepository.save(role);
	}
}
