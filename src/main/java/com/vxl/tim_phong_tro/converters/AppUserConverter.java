package com.vxl.tim_phong_tro.converters;

import com.vxl.tim_phong_tro.models.dtos.AuthToken;
import com.vxl.tim_phong_tro.models.dtos.UserInfoDto;
import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.UserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppUserConverter {
    private final ModelMapper modelMapper;

    public AppUser requestToModel(AuthToken signupRequest){
        return modelMapper.map(signupRequest,AppUser.class);
    }
    public UserInfoDto userInfoEntityToDTO(UserInfo userInfo){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userInfo,UserInfoDto.class);
    }

    public UserInfo userInfoDtoToEntity(UserInfoDto userInfoDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userInfoDto,UserInfo.class);
    }
    public UserInfoDto userInfoEntityToDto(UserInfo userInfo){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userInfo,UserInfoDto.class);
    }
}