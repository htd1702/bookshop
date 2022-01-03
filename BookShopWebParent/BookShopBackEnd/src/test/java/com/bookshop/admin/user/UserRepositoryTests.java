package com.bookshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.bookshop.common.entity.Role;
import com.bookshop.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User user = new User("admin@ducdev.com", "1234", "Duc", "Hoang");
		user.addRole(roleAdmin);

		User savedUSer = repo.save(user);
		assertThat(savedUSer.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateUserWithTwoRole() {
		User userTest = new User("test@gmail.com", "test123", "Test", "TwoRoleUser");

		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userTest.addRole(roleEditor);
		userTest.addRole(roleAssistant);

		User savedUser = repo.save(userTest);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUser() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));

	}

	@Test
	public void testGetUserById() {
		User userDuc = repo.findById(1).get();
		System.out.println(userDuc);
		assertThat(userDuc).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		User userTest = repo.findById(1).get();
		userTest.setEnabled(true);
		userTest.setEmail("abc@gmail.com");
		
		repo.save(userTest);
	}
	
	@Test
	public void testUpdateUserRole() {
		User userTest = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSaleperson = new Role(2);
		userTest.getRoles().remove(roleEditor);
		userTest.addRole(roleSaleperson);
		
		repo.save(userTest);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String mail = "abc@test.com";
		User user = repo.getUserByEmail(mail);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testGetImagePath() {
		
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUser = page.getContent();
		
		listUser.forEach(user -> System.out.println(user));
		
		assertThat(listUser.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testFindByKeyword() {
		String keyword = "bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword,pageable);
		
		List<User> listUser = page.getContent();
		
		listUser.forEach(user -> System.out.println(user));
		
		assertThat(listUser.size()).isGreaterThan(0);
	}
}
