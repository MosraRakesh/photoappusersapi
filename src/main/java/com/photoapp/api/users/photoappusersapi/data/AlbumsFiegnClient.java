package com.photoapp.api.users.photoappusersapi.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.photoapp.api.users.photoappusersapi.ui.response.model.AlbumResponseModel;


@FeignClient("albums-ws")
public interface AlbumsFiegnClient {
	@GetMapping("/users/{id}/albums")
	 public List<AlbumResponseModel> getAlbums(@PathVariable String id);	
}
	
