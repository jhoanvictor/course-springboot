package com.coursejava.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.coursejava.course.entities.Order;
import com.coursejava.course.entities.OrderItem;
import com.coursejava.course.repositories.OrderRepository;
import com.coursejava.course.services.exceptions.DatabaseException;
import com.coursejava.course.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderItemService orderItemService;

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}

	public Order insert(Order obj) {
		obj = repository.save(obj);
		for (OrderItem item : obj.getItems()) {
			item.setOrder(obj);
			orderItemService.insert(item);
		}
		return obj;
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Order update(Long id, Order obj) {

		try {
			Order entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Order entity, Order obj) {
		entity.setOrderStatus(obj.getOrderStatus());
		System.out.println(entity.getOrderStatus());
	}

}
