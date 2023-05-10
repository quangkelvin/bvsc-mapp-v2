package dao;

import java.util.Optional;

import model.entity.UserInfo;

public interface AuthDao<T>{
	Optional<T> findByUserNameAndPassword(String username,String password);

}
