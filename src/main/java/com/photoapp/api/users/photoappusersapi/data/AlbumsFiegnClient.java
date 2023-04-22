package com.photoapp.api.users.photoappusersapi.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.photoapp.api.users.photoappusersapi.ui.response.model.AlbumResponseModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@FeignClient(name="albums-ws")
public interface AlbumsFiegnClient {
	@GetMapping("/users/{id}/albums")
	@CircuitBreaker(name="albums-ws",fallbackMethod="getAlbumsFallback")
	 public List<AlbumResponseModel> getAlbums(@PathVariable String id);	


/*@Component
class AlbumsFallBack implements AlbumsFiegnClient{

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}
	*/
//}

default List<AlbumResponseModel>  getAlbumsFallback(String id,Throwable exception){
	System.out.println("param="+id);
	System.out.println("exception took place"  +exception.getMessage());
	return new ArrayList<>();
	
}
}

