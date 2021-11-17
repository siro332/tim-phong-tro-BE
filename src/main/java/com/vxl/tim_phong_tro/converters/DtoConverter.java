package com.vxl.tim_phong_tro.converters;

import com.vxl.tim_phong_tro.models.dtos.*;
import com.vxl.tim_phong_tro.models.entities.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoConverter {
    private final ModelMapper modelMapper;

    public AppUser requestToModel(AuthToken signupRequest){
        return modelMapper.map(signupRequest,AppUser.class);
    }
    public UserInfoDto userInfoEntityToDTO(UserInfo userInfo){
        return modelMapper.map(userInfo,UserInfoDto.class);
    }
    public PostPreviewDto userPostEntityToPostPreviewDto(UserPost post){
        return modelMapper.map(post,PostPreviewDto.class);
    }
    public PostDto userPostEntityToPostDto(UserPost post){
        return modelMapper.map(post,PostDto.class);
    }

    public UserInfoDto userInfoEntityToDto(UserInfo userInfo){
        return modelMapper.map(userInfo,UserInfoDto.class);
    }

    public StreetAddress addressDtoToEntity(StreetAddressDto addressDto){return modelMapper.map(addressDto,StreetAddress.class);}
    public RoomInfo roomInfoDtoToEntity(RoomInfoDto roomInfoDto){return modelMapper.map(roomInfoDto,RoomInfo.class);}
    public UserPost postFormToEntity(PostForm form){return modelMapper.map(form,UserPost.class);}
}
