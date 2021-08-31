package com.vxl.tim_phong_tro.converters;

import com.vxl.tim_phong_tro.models.dtos.AuthToken;
import com.vxl.tim_phong_tro.models.dtos.UserInfoDto;
import com.vxl.tim_phong_tro.models.dtos.UserPostDto;
import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.UserInfo;
import com.vxl.tim_phong_tro.models.entities.UserPost;
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

    public UserInfo userInfoDtoToEntity(UserInfoDto userInfoDto){
        return modelMapper.map(userInfoDto,UserInfo.class);
    }
    public UserInfoDto userInfoEntityToDto(UserInfo userInfo){
        return modelMapper.map(userInfo,UserInfoDto.class);
    }

    public UserPostDto userPostEntityToDto(UserPost userPost){
        return modelMapper.map(userPost,UserPostDto.class);
    }
}
